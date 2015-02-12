package com.tipster.betfair

import com.tipster.betfair.error.BetfairError
import com.tipster.betfair.exceptions.BetfairWrapperException
import com.tipster.betfair.util.json.JsonConverter
import com.tipster.betfair.utils.http.JsonRpcRequest
import grails.transaction.Transactional
import groovy.json.internal.LazyMap
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Transactional
class BetfairApiService extends BaseService {

    public static final Integer BETFAIR_API_ID = 1
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
                if (json[0].result) {
                    return json[0]
                }

                if (json[0].error instanceof LazyMap) {
                    BetfairError betfairError = this.processError((LazyMap) json[0].error)
                    throw new BetfairWrapperException(betfairError: betfairError)
                } else {
                    log.error "Something is very wrong here. This occassion should never happen."
                }
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
}
