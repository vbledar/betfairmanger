package com.tipster.betfair.betting

import com.tipster.betfair.BetfairApiService
import com.tipster.betfair.Country
import com.tipster.betfair.CountryInformation
import com.tipster.betfair.MarketFilter
import com.tipster.betfair.event.Competition
import com.tipster.betfair.utils.http.JsonRpcRequest
import grails.transaction.Transactional
import groovy.json.JsonSlurper

@Transactional
class CompetitionsService {

    def grailsApplication
    def betfairApiService

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

//        def jsonSlurper = new JsonSlurper()
//        def competitionResults = jsonSlurper.parseText(jsonResponse.result)
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
}
