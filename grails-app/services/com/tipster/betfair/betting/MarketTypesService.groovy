package com.tipster.betfair.betting

import com.tipster.betfair.BetfairApiService
import com.tipster.betfair.Country
import com.tipster.betfair.CountryInformation
import com.tipster.betfair.MarketFilter
import com.tipster.betfair.event.Competition
import com.tipster.betfair.event.MarketType
import com.tipster.betfair.utils.http.JsonRpcRequest
import grails.transaction.Transactional

@Transactional
class MarketTypesService {

    def grailsApplication
    def betfairApiService

    def retrieveMarketTypesFromBetfair() {
        String api = grailsApplication.config.betfair.api.betting.bettingApi
        String apiVersion = grailsApplication.config.betfair.api.betting.bettingApiVersion
        String action = grailsApplication.config.betfair.api.betting.actionListMarketTypes

        MarketFilter marketFilter = new MarketFilter()
        marketFilter.addExchangeId("1")                 // by default UK marketplace
        marketFilter.addEventTypeId("1")                // by default only soccer

        Map<String, Object> params = new HashMap<>()
        params.put("filter", marketFilter)

        JsonRpcRequest rpcRequest = new JsonRpcRequest()
        rpcRequest.id = BetfairApiService.BETFAIR_API_ID
        rpcRequest.jsonrpc = BetfairApiService.BETFAIR_JSON_RPC_VERSION
        rpcRequest.method = api + apiVersion + action
        rpcRequest.params = params

        def jsonResponse = betfairApiService.executeBetfairApiCall(rpcRequest)
        def marketTypes = new ArrayList<MarketType>(1)

        MarketType marketType
        for (def marketTypesResults : jsonResponse?.result) {
            for (def marketTypeResult : marketTypesResults) {
                log.debug "Attempting to process: " + marketTypeResult
                try {
                    if (marketTypeResult.marketType) {
                        log.debug "Market type: " + marketTypeResult.marketType

                        // create a new market type instance
                        marketType = new MarketType(name: marketTypeResult.marketType)

                        // attempt to persist the competition instance
                        marketType = marketType.merge()
                        if (!marketType.validate()) {
                            log.error "Failed to persist market type with name [" + marketType?.name + "]."
                            marketType?.errors?.each {
                                log.error it
                            }
                        } else {
                            marketTypes.add(marketType)
                        }
                    }
                } catch (ex) {
                    log.error "An unknown exception occurred.", ex
                }
            }
        }

        return marketTypes
    }

    def updateMarketTypeAutomaticRetrievalState(MarketType marketType) {
        marketType.automaticRetrieval = !marketType.automaticRetrieval
        if (!marketType.save()) {
            log.error "Could not update market type's automatic retrieval state."
            marketType.errors.each {
                log.error it
            }
            return false
        }
        return true
    }
}
