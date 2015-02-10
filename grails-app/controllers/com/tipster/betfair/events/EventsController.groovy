package com.tipster.betfair.events

import com.tipster.BaseController
import com.tipster.betfair.Country

class EventsController extends BaseController {

    def eventsService

    def manageCountries() {
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
            def countries = eventsService.getListOfCountries(Boolean.FALSE)
            render template: 'country/countriesList', model: [countries: countries]
        } catch (ex) {
            log.error "Failed to retrieve countries from betfair.", ex
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'countries.management.retrieve.countries.failed')]
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
