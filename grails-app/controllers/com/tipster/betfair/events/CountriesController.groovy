package com.tipster.betfair.events

import com.tipster.BaseController
import com.tipster.betfair.Country
import com.tipster.betfair.exceptions.BetfairWrapperException

class CountriesController extends BaseController {

    def eventsService
    def countriesService

    def manageCountries() {

        if (!params.max) params.max = 15
        if (!params.offset) params.offset = 0

        def countries = Country.list(params)
        render view: 'country/manageCountries', model: [countries: countries]
    }

    def manageCountriesFiltered() {
        def countries = Country.list(params)
        render template: 'country/persistedCountriesFiltered', model: [countries: countries]
    }

    def setCountryAutomaticRetrieval() {
        Country country = Country.findByCountryCode(params.countryCode)
        if (!country) {
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'countries.management.country.for.code.not.found', args: [params.countryCode])]
            }
            return
        }

        if (!countriesService.updateCountryAutomaticRetrievalState(country)) {
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'countries.management.country.not.updated', args: [params.countryCode])]
            }
        }

        render (contentType: 'application/json') {
            ['success': true, 'message': message(code: 'countries.management.country.updated.successfully', args: [params.countryCode])]
        }

    }

    def retrieveBetfairCountries() {
        try {
            log.info "Retrieving countries from betfair."
            eventsService.synchronizeCountriesFromBetfair()

            if (!params.max) params.max = 20
            def countries = Country.list(params)

            render template: 'country/persistedCountriesFiltered', model: [countries: countries]
        } catch (BetfairWrapperException betfairWrapperException) {
            log.error "Failed to retrieve countries from betfair.", betfairWrapperException

            render (contentType: 'application/json') {
                ['success': false, 'message': betfairWrapperException?.getErrorDescription()]
            }
        } catch (ex) {
            log.error "Failed to retrieve countries from betfair.", ex
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: ex.getMessage() ? ex.getMessage() : 'countries.management.retrieve.countries.failed')]
            }
        }
    }

    def persistBetfairCountries() {
        try {
            log.info "Persisting countries from betfair."
            def countries = eventsService.getListOfCountries(Boolean.TRUE);
            render template: 'country/countriesList', model: [countries: countries]
        } catch(ex) {
            log.error "Failed to persist countries from betfair.", ex
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'countries.management.retrieve.countries.failed')]
            }
        }
    }
}
