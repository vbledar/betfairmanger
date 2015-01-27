package com.tipster.betfair.accounts

import com.tipster.betfair.LoginService
import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.commons.GrailsApplication
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(AccountService)
class AccountServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        given:
        def loginMock = mockService(LoginService)
        service.loginService = loginMock
        service.grailsApplication = grailsApplication

        when:
        def response = service.retrieveApplicationKeys()
        log.error "I'm in when."

        then:
        response == Boolean.TRUE
    }
}
