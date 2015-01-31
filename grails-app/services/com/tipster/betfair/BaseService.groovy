package com.tipster.betfair

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
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

}
