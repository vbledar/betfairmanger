package com.tipster.betfair.events

import com.tipster.BaseController
import com.tipster.betfair.Country
import com.tipster.betfair.event.Competition
import com.tipster.betfair.event.MarketType

class CompetitionsController extends BaseController {

    def competitionsService

    def manageCompetitions() {
        if (!params.max) params.max = 15
        if (!params.offset) params.offset = 0

        def competitions = competitionsService.retrieveCompetitions(params)
        render view: 'manageCompetitions', model: [competitions: competitions]
    }

    def manageCompetitionsBoxContainer() {
        if (!params.max) params.max = 15
        if (!params.offset) params.offset = 0

        def competitions = competitionsService.retrieveCompetitions(params)

        render template: 'competitionsBoxContainer', model: [competitions: competitions]
    }

    def filteredCompetitions() {
        if (!params.max) params.max = 15
        if (!params.offset) params.offset = 0

        def competitions = competitionsService.retrieveCompetitions(params)

        render template: 'persistedCompetitions', model: [competitions: competitions]
    }

    def setCompetitionAutomaticRetrieval() {
        Competition competition = Competition.findByCompetitionId(params.competitionId)
        if (!competition) {
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'competitions.management.competition.for.competition.id.not.found', args: [params.competitionId])]
            }
            return
        }

        if (!competitionsService.updateCompetitionAutomaticRetrievalState(competition)) {
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'competitions.management.competition.not.updated', args: [competition?.competitionName])]
            }
        }

        render (contentType: 'application/json') {
            ['success': true, 'message': message(code: 'competitions.management.competition.updated.successfully', args: [competition?.competitionName])]
        }

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
