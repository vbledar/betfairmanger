package com.tipster.betfair.events

import com.tipster.betfair.Country

class EventsController {

    def eventsService

    def manageCountries() {
        def countries = Country.findAll()
        render view: 'country/manageCountries', model: [countries: countries]
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
