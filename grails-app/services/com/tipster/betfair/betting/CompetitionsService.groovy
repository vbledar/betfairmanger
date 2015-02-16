package com.tipster.betfair.betting

import com.tipster.betfair.BetfairApiService
import com.tipster.betfair.Country
import com.tipster.betfair.CountryInformation
import com.tipster.betfair.MarketFilter
import com.tipster.betfair.event.Competition
import com.tipster.betfair.event.MarketType
import com.tipster.betfair.util.json.JsonConverter
import com.tipster.betfair.utils.http.JsonRpcRequest
import grails.transaction.Transactional
import groovy.json.JsonSlurper
import groovy.json.internal.LazyMap

@Transactional
class CompetitionsService {

    def grailsApplication
    def betfairApiService

    def retrieveCompetitionsFromBetfairForCountry(Country country) {
        String api = grailsApplication.config.betfair.api.betting.bettingApi
        String apiVersion = grailsApplication.config.betfair.api.betting.bettingApiVersion
        String action = grailsApplication.config.betfair.api.betting.actionListCompetitions

        MarketFilter marketFilter = new MarketFilter()
        marketFilter.addExchangeId("1")                         // by default UK marketplace
        marketFilter.addEventTypeId("1")                        // by default only soccer
        marketFilter.addMarketCountry(country.countryCode)      // add country

        Map<String, Object> params = new HashMap<>()
        params.put("filter", marketFilter)

        JsonRpcRequest rpcRequest = new JsonRpcRequest()
        rpcRequest.id = BetfairApiService.BETFAIR_API_ID
        rpcRequest.jsonrpc = BetfairApiService.BETFAIR_JSON_RPC_VERSION
        rpcRequest.method = api + apiVersion + action
        rpcRequest.params = params

        def jsonResponse = betfairApiService.executeBetfairApiCall(rpcRequest)
        def competitions = new ArrayList<Competition>(1)

        Competition competition

        log.debug jsonResponse.result
        log.debug jsonResponse.result[0]
        log.debug jsonResponse?.result?.competition
        for (def competitionResults : jsonResponse.result) {
            LazyMap competitionRecord = (LazyMap) competitionResults
            log.debug "Competition record is: " + competitionRecord?.toString()
            if (competitionRecord.containsKey("competition")) {
                try {
                    log.debug "Found competition"
                    LazyMap competitionInformationRecord = (LazyMap) competitionRecord.get("competition")
                    if (competitionInformationRecord.containsKey("id")) {
                        competition = Competition.findByCompetitionId(competitionInformationRecord.get("id"))
                        if (competition) continue

                        // create a new competition instance
                        competition = new Competition(competitionId: competitionInformationRecord.get("id"), competitionName: competitionInformationRecord.get("name"), country: country)

                        if (!competition.save()) {
                            log.error "Failed to persist competition with id [" + competitionInformationRecord.get("id") + "] and name [" + competitionInformationRecord.get("name") + "]."
                            competition?.errors?.each {
                                log.error it
                            }
                        }
                    }
                } catch (ex) {
                    log.error "An unhandable exception occurred.", ex
                }
            }
        }

        country.competitionsCounter = Country.countByCountryCode(country.countryCode)
        if (!country.save()) {
            log.error "Failed to update country's competition counter."
            country.errors.each {
                log.error it
            }
        }
    }

    def retrieveCompetitionsFromBetfair(Set<Country> countries) {
        String api = grailsApplication.config.betfair.api.betting.bettingApi
        String apiVersion = grailsApplication.config.betfair.api.betting.bettingApiVersion
        String action = grailsApplication.config.betfair.api.betting.actionListCompetitions

        MarketFilter marketFilter = new MarketFilter()
        marketFilter.addExchangeId("1")                 // by default UK marketplace
        marketFilter.addEventTypeId("1")                // by default only soccer

        // apply any countries in the market filter
        countries?.each {
            marketFilter.addMarketCountry(it.countryCode)
        }

        Map<String, Object> params = new HashMap<>()
        params.put("filter", marketFilter)

        JsonRpcRequest rpcRequest = new JsonRpcRequest()
        rpcRequest.id = BetfairApiService.BETFAIR_API_ID
        rpcRequest.jsonrpc = BetfairApiService.BETFAIR_JSON_RPC_VERSION
        rpcRequest.method = api + apiVersion + action
        rpcRequest.params = params

        def jsonResponse = betfairApiService.executeBetfairApiCall(rpcRequest)
        def competitions = new ArrayList<Competition>(1)

        Competition competition

        for (def competitionResults : jsonResponse.result) {
            for (def competitionResult : competitionResults) {
                log.debug "Attempting to process: " + competitionResult
                try {
                    if (competitionResult.competition) {
                        log.debug "Competition region: " + competitionResult.competitionRegion
                        log.debug "Competition id: " + competitionResult.competition.id
                        log.debug "Competition name: " + competitionResult.competition.name

                        // attempt to find the country information for competition
                        String country3LetterCode = competitionResult.competitionRegion
                        CountryInformation countryInformation = CountryInformation.findByIso3LetterCode(country3LetterCode)
                        Country country = Country.findByCountryCode(countryInformation?.iso2LetterCode)

                        // create a new competition instance
                        competition = new Competition(competitionId: competitionResult?.competition?.id, competitionName: competitionResult?.competition?.name, country: country)

                        // attempt to persist the competition instance
                        competition = competition.merge()
                        if (!competition.validate()) {
                            log.error "Failed to persist competition with id [" + competitionResult?.competition?.id + "] and name [" + competitionResult?.competition?.name + "]."
                            competition?.errors?.each {
                                log.error it
                            }
                        } else {
                            competitions.add(competition)
                        }
                    }
                } catch (ex) {
                    log.error "An unhandable exception occurred.", ex
                }
            }
        }

        return competitions
    }

    def retrieveCompetitionsByCountry(Country country) {
        def criteria = Competition.createCriteria()
        def results = criteria.list {
            eq ('country', country)
        }
    }

    def retrieveCompetitions(params) {
        def criteria = Competition.createCriteria()
        def results = criteria.list(max: params.max, offset: params.offset) {
            if (params.countryCode != null && !params.countryCode.isEmpty()) {
                log.debug "Country code is: " + params.countryCode
                Country country = Country.findByCountryCode(params.countryCode)
                eq ('country', country)
            }
        }
    }

    def updateCompetitionAutomaticRetrievalState(Competition competition) {
        competition.automaticRetrieval = !competition.automaticRetrieval
        if (!competition.save()) {
            log.error "Could not update competition's automatic retrieval state."
            competition.errors.each {
                log.error it
            }
            return false
        }
        return true
    }
}
