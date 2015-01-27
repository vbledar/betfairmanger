package com.tipster.betfair.betting

import com.tipster.betfair.BaseService
import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Transactional
class EventsService extends BaseService {

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
