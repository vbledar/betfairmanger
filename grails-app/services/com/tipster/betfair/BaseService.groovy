package com.tipster.betfair

import grails.transaction.Transactional

@Transactional
abstract class BaseService {

    def grailsApplication

    def loginService            // use this service to retrieve a session token either from memory or from betfair

}
