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
    def retrieveApplicationKeys(Boolean persist) {

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
                log.info "Request was successful."
                log.info "Processing retrieved accounts information from betfair."

                log.debug "Response data are: "
                log.debug json

                if (json.error) {
                    Boolean errorProcess = this.processError(json)
                    return
                }

                log.debug "Create an array list to store developer application retrieved from betfair."
                def developerApps = new ArrayList(1)

                log.debug "About to process retrieved information."
                for (def developerAppJson : json.result) {
                    DeveloperApp developerApp = new DeveloperApp()
                    developerApp.appId = developerAppJson.appId
                    developerApp.appName = developerAppJson.appName

                    log.debug "Processing application " + developerApp.appName

                    def appVersions = developerAppJson.appVersions

                    log.debug "About to process application versions retrieved information"
                    for (def appVersion : appVersions) {
                        log.debug "Application version: " + appVersion
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

                        log.debug "Application key processed " + developerAppVersion.applicationKey
                    }

                    developerApps.add(developerApp)
                }

                if (persist) {
                    developerApps.each {
                        DeveloperApp developerApp = DeveloperApp.findByAppId(it.appId)
                        if (developerApp) {
                            log.debug "Developer application account found in database. It will be updated."
                            it.id = developerApp.id
                        }

                        if (!it.save()) {
                            log.error "Failed to persist developer application account"
                            it.errors.each {
                                log.error it
                            }
                        }
                    }
                }

                return developerApps
            }
        }
    }

    def deleteAccount(DeveloperAppVersion developerAppVersion) {

    }
}
