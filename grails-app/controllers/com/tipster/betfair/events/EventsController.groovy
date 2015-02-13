package com.tipster.betfair.events

import com.tipster.betfair.event.Competition
import com.tipster.betfair.event.Event
import com.tipster.betfair.event.Market
import com.tipster.betfair.event.MarketType

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
            render template: '/events/persistedEvents', model: [eventsList: eventsList]
        } catch (ex) {
            log.error "Failed to synchronize events from BetFair.", ex
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'events.management.retrieve.events.failed')]
            }
        }
    }

    def synchronizeMarketsFromBetfair() {
        try {

            Event event = Event.findById(params.eventId)
            if (!event) {
                render (contentType: 'application/json') {
                    ['success': false, 'message': message(code: 'events.management.event.for.id.not.found', args: [params.eventId])]
                }
                return
            }

            Set<MarketType> marketTypes = new HashSet<>()
            marketTypes.add(MarketType.findByName("MATCH_ODDS"))
            marketTypes.add(MarketType.findByName("OVER_UNDER_05"))
            marketTypes.add(MarketType.findByName("OVER_UNDER_15"))
            marketTypes.add(MarketType.findByName("OVER_UNDER_25"))
            marketTypes.add(MarketType.findByName("OVER_UNDER_35"))
            marketTypes.add(MarketType.findByName("OVER_UNDER_45"))
            marketTypes.add(MarketType.findByName("OVER_UNDER_55"))
            marketTypes.add(MarketType.findByName("OVER_UNDER_65"))
            marketTypes.add(MarketType.findByName("HALF_TIME_FULL_TIME"))

            eventsService.synchronizeEventMarketsFromBetfair(event, marketTypes)

            def markets = eventsService.getMarketsByEvent(event)

            for (def market : markets) {
                try {
                    eventsService.synchronizeEventMarketOddsFromBetfair(market)
                } catch(ex) {
                    log.error "Failed to retrieve odds for market with id [" + market?.marketId + "]."
                }
            }
            render template: 'persistedMarkets', model: [markets: markets]
        } catch (ex) {
            log.error "Failed to synchronize markets from BetFair.", ex
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'markets.management.retrieve.markets.failed')]
            }
        }
    }

    def manageEventMarkets() {
        Event event = Event.findById(params.eventId)
        if (!event) {
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'events.management.event.for.id.not.found', args: [params.eventId])]
            }
            return
        }

        def markets = eventsService.getMarketsByEvent(event)
        render template: 'mainMarketsInformationPanel', model: [event: event, markets: markets]
    }

    def renderMarketRunners() {
        Market market = Market.findByMarketId(params.marketId)
        if (!market) {
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'markets.management.market.for.id.not.found', args: [params.marketId])]
            }
            return
        }

        def runners = market.runners.sort { it.sortPriority }
        render template: 'runnersList', model: [market: market, runners: runners]
    }
}
