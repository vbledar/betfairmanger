package com.tipster.betfair.betting

import com.tipster.betfair.BaseService
import com.tipster.betfair.Country
import com.tipster.betfair.MarketFilter
import com.tipster.betfair.error.BetfairError
import com.tipster.betfair.exceptions.BetfairWrapperException
import com.tipster.betfair.util.json.JsonConverter
import com.tipster.betfair.utils.http.JsonRpcRequest
import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Transactional
class EventsService extends BaseService {

    public static final String BETFAIR_API_ID = "1"
    public static final String BETFAIR_JSON_RPC_VERSION = "2.0"

    def executeBetfairApiCall(JsonRpcRequest rpcRequest) {
        String sessionToken = loginService.retrieveSessionToken()
        String applicationKey = grailsApplication.config.betfair.applicationKey
        String endpoint = grailsApplication.config.betfair.api.betting.exchangeEndpoint

        log.debug JsonConverter.convertToJson(rpcRequest)

        def http = new HTTPBuilder(endpoint)
        http.request(Method.POST, ContentType.JSON) { request ->
            headers."X-Application" = applicationKey
            headers."X-Authentication" = sessionToken
            body = [JsonConverter.convertToJson(rpcRequest)]

            response.success = { response, json ->
                log.info "Request was successful for method: [" + rpcRequest.method + "]."
                if (json.error) {
                    log.info "Betfair response has errors."
                    BetfairError betfairError = this.processError(json)
                    throw new BetfairWrapperException(betfairError: betfairError)
                }
                return json
            }

            response.failure = {response ->
                log.info "Request was unsuccessful for method [" + rpcRequest.method + "]."
                log.error "Retrieve response from server is: "
                log.error "Response status is: [" + response.status + "]."
                log.error response
                throw new Exception("Request produced an error. Please, check error logs for more information.")
            }
        }
    }

    def getListOfCountries(Boolean persist) {
        String api = grailsApplication.config.betfair.api.betting.bettingApi
        String apiVersion = grailsApplication.config.betfair.api.betting.bettingApiVersion
        String action = grailsApplication.config.betfair.api.betting.actionListCountries

        MarketFilter marketFilter = new MarketFilter()
        marketFilter.addExchangeId("1")
        Map<String, Object> params = new HashMap<>()
        params.put("filter", marketFilter)

        JsonRpcRequest rpcRequest = new JsonRpcRequest()
        rpcRequest.id = BETFAIR_API_ID
        rpcRequest.jsonrpc = BETFAIR_JSON_RPC_VERSION
        rpcRequest.method = api + apiVersion + action
        rpcRequest.params = params


        def jsonResponse = executeBetfairApiCall(rpcRequest)

        log.debug "Create an array list to store countries retrieved from betfair."
        def countries = new ArrayList(1)

        log.debug "About to process retrieved information."
        for (def countryCode : json.result) {
            Country country = new Country(countryCode: countryCode.countryCode)
            countries.add(country)
        }

        if (persist) {
            countries.each {
                if (!Country.findByCountryCode(it.countryCode)) {
                    if (!it.save()) {
                        log.error "Failed to persist country."
                        it.errors.each {
                            log.error it
                        }
                    }
                }
            }
        }

        return countries
    }


    def getListOfCompetitions(Boolean persist) {
        String sessionToken = loginService.retrieveSessionToken()

        String applicationKey = grailsApplication.config.betfair.applicationKey

        String endpoint = grailsApplication.config.betfair.api.betting.exchangeEndpoint
        String api = grailsApplication.config.betfair.api.betting.bettingApi
        String apiVersion = grailsApplication.config.betfair.api.betting.bettingApiVersion
        String action = grailsApplication.config.betfair.api.betting.actionListCompetitions

        log.debug "Endpoint: " + endpoint
        log.debug "API: " + api
        log.debug "Version: " + apiVersion
        log.debug "Action: " + action

        String method = api + apiVersion + action
        log.debug "Generated method is: " + method

        def http = new HTTPBuilder(endpoint)
        http.request(Method.POST, ContentType.JSON) { request ->
            headers."X-Application" = applicationKey
            headers."X-Authentication" = sessionToken
            body = [
                    id: 1,
                    jsonrpc: "2.0",
                    method: method,
                    params: [
                            filter: [
                                    exchangeIds: [
                                            1
                                    ]
                            ]
                    ]
            ]

            response.success = {response, json ->

                log.info "Request was successful."
                log.info "Processing retrieved countries information from betfair."

                log.debug "Response data are: "
                log.debug json

                if (json.error) {
                    Boolean errorProcess = this.processError(json)
                    return
                }

                log.debug "Create an array list to store countries retrieved from betfair."
                def countries = new ArrayList(1)

                log.debug "About to process retrieved information."
                for (def countryCode : json.result) {
                    Country country = new Country(countryCode: countryCode.countryCode)
                    countries.add(country)
                }

                if (persist) {
                    countries.each {
                        if (!Country.findByCountryCode(it.countryCode)) {
                            if (!it.save()) {
                                log.error "Failed to persist country."
                                it.errors.each {
                                    log.error it
                                }
                            }
                        }
                    }
                }

                return countries
            }
        }
    }

    def getEventTypes() {
        String sessionToken = loginService.retrieveSessionToken()

        String endpoint = grailsApplication.config.betfair.api.betting.exchangeEndpoint
        String api = grailsApplication.config.betfair.api.betting.bettingApi
        String apiVersion = grailsApplication.config.betfair.api.betting.bettingApiVersion
        String action = grailsApplication.config.betfair.api.betting.actionListEvents

        log.debug "Endpoint: " + endpoint
        log.debug "API: " + api
        log.debug "Version: " + apiVersion
        log.debug "Action: " + action

        String method = api + apiVersion + action
        log.debug "Generated method is: " + method

        def http = new HTTPBuilder(endpoint)
        http.request(Method.POST, ContentType.JSON) { request ->
            headers."X-Authentication" = sessionToken
            body = [
                    id: 1,
                    jsonrpc: "2.0",
                    method: method,
                    params: [ ]
            ]

            response.success = {response, json ->
                log.error "Response retrieved and is: "
                log.error response

                log.error "Response data are: "
                log.error json

                log.error "Result in response is: "
                log.error json.result
            }
        }
    }

}
