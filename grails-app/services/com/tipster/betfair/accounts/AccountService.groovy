package com.tipster.betfair.accounts

import com.opensymphony.sitemesh.Content
import com.tipster.betfair.BaseService
import grails.transaction.Transactional
import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
import org.codehaus.groovy.grails.web.binding.bindingsource.JsonDataBindingSourceCreator
import org.codehaus.groovy.grails.web.json.JSONArray
import grails.converters.*
import org.codehaus.groovy.grails.web.json.*;
import org.omg.CORBA.REBIND

@Transactional
class AccountService extends BaseService {

    /**
     * Retrieve application
     */
    def retrieveApplicationKeys() {

        String sessionToken = loginService.retrieveSessionToken()

        String accountsEndpoint = grailsApplication.config.betfair.api.accounts.accountsEndpoint
        String accountsApi = grailsApplication.config.betfair.api.accounts.accountsApi
        String accountsApiVersion = grailsApplication.config.betfair.api.accounts.accountsApiVersion
        String actionApplicationKeys = grailsApplication.config.betfair.api.accounts.actionApplicationKeys

        log.debug "Endpoint: " + accountsEndpoint
        log.debug "API: " + accountsApi
        log.debug "Version: " + accountsApiVersion
        log.debug "Action: " + actionApplicationKeys

        String method = accountsApi + accountsApiVersion + actionApplicationKeys
        log.debug "Generated method is: " + method

        def http = new HTTPBuilder(accountsEndpoint)
        http.request(Method.POST, ContentType.JSON) { request ->
            headers."X-Authentication" = sessionToken
            body = [
                    id: 1,
                    jsonrpc: "2.0",
                    method: method,
                    params: [ ]
            ]

            response.success = {response, json ->

                log.error "Result in response is: "
                log.error json.result


                DeveloperApp accounts = new DeveloperApp()
                accounts.appId = json.result.appId
                accounts.appName = json.result.appName

                def jsonObject = JSON.parse(json.result.appVersions)
                if (jsonObject instanceof Map) {
                    log.error "Retrieve a json map."
                }

                if (jsonObject instanceof JSONArray) {
                    log.error "Retrieve a json array"
                }

                DeveloperApp developerApp = new DeveloperApp(new JsonSlurper().parseText(json.result[0]))
                log.error "Developer application keys: " + developerApp.appName
            }
        }
    }
}
