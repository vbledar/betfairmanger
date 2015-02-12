package com.tipster.betfair.events

import com.tipster.betfair.Country
import com.tipster.betfair.event.Competition

class EventsController {

    def eventsService

    def synchronizeEventsFromBetfair() {
        try {
            Competition competition = Competition.findByCompetitionId(params.competitionId)
            if (!competition) {
                render (contentType: 'application/json') {
                    ['success': false, 'message': message(code: 'competitions.management.competition.for.competition.id.not.found', args: [params.competitionId])]
                }
                return;
            }

            eventsService.synchronizeEventsFromBetfair(competition)

            def eventsList = eventsService.getEventsByCompetition(competition)
            render template: '/persistedEvents', model: [eventsList: eventsList]
        } catch (ex) {
            log.error "Failed to synchronize events from BetFair.", ex
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'events.management.retrieve.events.failed')]
            }
        }
    }

}
