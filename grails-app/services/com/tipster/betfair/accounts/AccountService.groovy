package com.tipster.betfair.accounts

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.opensymphony.sitemesh.Content
import com.tipster.betfair.BaseService
import grails.transaction.Transactional
import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.RESTClient
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
                log.debug "Response retrieved and is: "
                log.debug response

                log.debug "Response data are: "
                log.debug json

                def developerApps = new ArrayList(1)

                for (def developerAppJson : json.result) {
                    DeveloperApp developerApp = new DeveloperApp()
                    developerApp.appId = developerAppJson.appId
                    developerApp.appName = developerAppJson.appName

                    def appVersions = developerAppJson.appVersions
                    for (def appVersion : appVersions) {
                        log.error "Application version: " + appVersion
                        DeveloperAppVersion developerAppVersion = new DeveloperAppVersion()
                        developerAppVersion.active = appVersion.active
                        developerAppVersion.applicationKey = appVersion.applicationKey
                        developerAppVersion.delayData = appVersion.delayData
                        developerAppVersion.owner = appVersion.owner
                        developerAppVersion.ownerManaged = appVersion.ownerManaged
                        developerAppVersion.subscriptionRequired = appVersion.subscriptionRequired
                        developerAppVersion.version = appVersion.version
                        developerAppVersion.versionId = appVersion.versionId
                        developerApp.addToAppVersions(developerAppVersion)
                    }

                    developerApps.add(developerApp)
                }

                return developerApps
            }
        }
    }
}
