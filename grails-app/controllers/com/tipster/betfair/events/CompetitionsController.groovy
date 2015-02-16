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
            Country country = Country.findByCountryCode(params.countryCode)
            if (!country) {
                render (contentType: 'application/json') {
                    ['success': false, 'message': message(code: 'user.message.please.select.country')]
                }
            }

            competitionsService.retrieveCompetitionsFromBetfairForCountry(country)

            def competitions = competitionsService.retrieveCompetitions(params)
            render template: 'persistedCompetitions', model: [competitions: competitions, countryCode: params.countryCode]
        } catch (ex) {
            log.error "Failed to retrieve competitions from BetFair.", ex
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'competitions.management.retrieve.competitions.failed')]
            }
        }
    }
}
