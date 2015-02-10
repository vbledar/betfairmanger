package com.tipster.betfair.betting

import com.tipster.betfair.BetfairApiService
import com.tipster.betfair.Country
import com.tipster.betfair.CountryInformation
import com.tipster.betfair.MarketFilter
import com.tipster.betfair.event.Competition
import com.tipster.betfair.utils.http.JsonRpcRequest
import grails.transaction.Transactional

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
        jsonResponse?.result?.each {
            try {
                if (it.competition) {
                    log.debug "Competition region: " + it.competitionRegion
                    log.debug "Competition id: " + it.competition.id
                    log.debug "Competition name: " + it.competition.name

                    // attempt to find the country information for competition
                    String country3LetterCode = it.competitionRegion
                    CountryInformation countryInformation = CountryInformation.findByIso3LetterCode(country3LetterCode)
                    Country country = Country.findByCountryCode(countryInformation?.iso2LetterCode)

                    // create a new competition instance
                    competition = new Competition(competitionId: it?.competition?.id, competitionName: it?.competition?.name, country: country)

                    // attempt to persist the competition instance
                    if (!competition.save()) {
                        log.error "Failed to persist competition with id [" + it?.competition?.id + "] and name [" + it?.competition?.name + "]."
                        competition?.errors?.each {
                            log.error it
                        }
                    } else {
                        competitions.add(competition)
                    }
                }
            } catch(ex) {
                log.error "An unhandable exception occurred.", ex
            }
        }

        return competitions
    }
}
