package com.tipster

import com.tipster.betfair.Country
import com.tipster.betfair.event.Competition

class LandingController {

    def competitionsService
    def eventsService

    def index() {

        def countries = session["countries"]
        render (view: '/index', model: [countries: countries])

    }

    def ajaxGetCompetitionsByCountry() {
        Country country = Country.findByCountryCode(params.country)
        if (!country) {
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'countries.management.country.for.code.not.found', args: [params.country])]
            }
        }

        try {
            def competitions = competitionsService.retrieveCompetitionsByCountry(country)
            render template: '/mainInformationPanel', model: [competitions: competitions]
        } catch (ex) {
            render (contentType: 'application/json') {
                ['success': false, 'message': ex.message]
            }
        }

    }

    def ajaxGetEventByCompetition() {
        Competition competition = Competition.findByCompetitionId(params.competitionId)
        if (!competition) {
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'competitions.management.competition.for.competition.id.not.found', args: [params.competitionId])]
            }
        }

        try {
            def eventsList = eventsService.getEventsByCompetition(competition)
            render template: '/mainEventsInformationPanel', model: [eventsList: eventsList, competition: competition]
        } catch (ex) {
            render (contentType: 'application/json') {
                ['success': false, 'message': ex.message]
            }
        }

    }
}
