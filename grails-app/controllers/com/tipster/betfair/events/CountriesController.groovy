package com.tipster.betfair.events

import com.tipster.BaseController
import com.tipster.betfair.Country
import com.tipster.betfair.exceptions.BetfairWrapperException

class CountriesController extends BaseController {

    def eventsService

    def manageCountries() {

        if (!params.max) params.max = 20
        def countries = Country.list(params)
        render view: 'country/manageCountries', model: [countries: countries]
    }

    def manageCountriesFiltered() {
        def countries = Country.list(params)
        render template: 'country/persistedCountriesFiltered', model: [countries: countries]
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
