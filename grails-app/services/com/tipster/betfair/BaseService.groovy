package com.tipster.betfair

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.tipster.betfair.error.BetfairError
import com.tipster.betfair.error.BetfairException
import grails.transaction.Transactional
import groovy.json.internal.LazyMap

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

    def processError(LazyMap errorInformation) {

        BetfairError betfairError
        BetfairException betfairException

        String code = errorInformation.get("code")
        String message = errorInformation.get("message")
        betfairError = new BetfairError(code: code, message: message)

        if (errorInformation.containsKey("data")) {
            if (errorInformation.get("data") instanceof LazyMap) {
                LazyMap exceptionData = errorInformation.get("data")
                String exceptionName = exceptionData.get("exceptionname")
                if (exceptionData.containsKey(exceptionName)) {
                    LazyMap betfairExceptionData = exceptionData.get(exceptionName)

                    def errorCode = betfairExceptionData.get("errorCode")
                    def errorDetails = betfairExceptionData.get("errorDetails")
                    def requestUUID = betfairExceptionData.get("requestUUID")

                    betfairException = new BetfairException(exceptionName: exceptionName, errorCode: errorCode, errorDetails: errorDetails, requestUUID: requestUUID, betfairError: betfairError)
                    betfairError.betfairException = betfairException
                }
            }
        }

        if (!betfairError.save()) {
            log.error "Failed to persist betfair error."
            betfairError.errors.each {
                log.error it
            }
        } else {
            return betfairError
        }

    }
}
