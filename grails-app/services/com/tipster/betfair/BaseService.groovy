package com.tipster.betfair

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.tipster.betfair.error.BetfairError
import com.tipster.betfair.error.BetfairException
import grails.transaction.Transactional

@Transactional
abstract class BaseService {

    /*
    ObjectMapper from jackson library used in order to convert json to object instances
     */
    public static final ObjectMapper objectMapper = new ObjectMapper()

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    def grailsApplication

    def loginService            // use this service to retrieve a session token either from memory or from betfair

    def processError(def errorInformation) {

        if (errorInformation.error) {
            def errorObject = errorInformation.error
            BetfairException betfairException = null
            if (errorObject.data) {
                betfairException = BetfairException.createBetfairException(errorObject.data)
            }
            BetfairError betfairError = new BetfairError(code: errorObject.code, message: errorObject.message, betfairException: betfairException)
            if (!betfairError.save()) {
                log.error "Failed to persist betfair error."
                betfairError.errors.each {
                    log.error it
                }
            } else {
                return betfairError
            }
        } else {
            log.debug "No error entry in error information"
            log.debug errorInformation
        }

    }
}
