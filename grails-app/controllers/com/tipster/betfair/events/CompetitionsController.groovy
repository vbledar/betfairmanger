package com.tipster.betfair.events

import com.tipster.BaseController
import com.tipster.betfair.Country
import com.tipster.betfair.event.Competition

class CompetitionsController extends BaseController {

    def competitionsService

    def manageCompetitions() {
        if (!params.max) params.max = 15
        if (!params.offset) params.offset = 0

        def competitions = competitionsService.retrieveCompetitions(params)
        render view: 'manageCompetitions', model: [competitions: competitions]
    }

    def filteredCompetitions() {
        if (!params.max) params.max = 15
        if (!params.offset) params.offset = 0

        def competitions = competitionsService.retrieveCompetitions(params)
        render view: '_persistedCompetitions', model: [competitions: competitions]
    }

    def retrieveBetfairCompetitions() {
        try {
            log.info "Retrieving competitions from BetFair."
            Country country = Country.findByCountryCode("GB")
            Set<Country> countries = new HashSet<>(1)
            countries.add(country)

            def competitions = competitionsService.retrieveCompetitionsFromBetfair(countries)
            render template: 'persistedCompetitions', model: [competitions: competitions]
        } catch (ex) {
            log.error "Failed to retrieve competitions from BetFair.", ex
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'competitions.management.retrieve.competitions.failed')]
            }
        }
    }
}
