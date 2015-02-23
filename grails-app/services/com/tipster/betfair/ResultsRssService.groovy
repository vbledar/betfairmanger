package com.tipster.betfair

import com.tipster.betfair.event.Event
import com.tipster.betfair.event.Market
import com.tipster.betfair.event.ResultFeedEntry
import com.tipster.betfair.event.Runner
import grails.transaction.Transactional
import groovyx.net.http.URIBuilder

import java.text.SimpleDateFormat

@Transactional
class ResultsRssService {

    public static final String FORMAT_PARAM = "format"
    public static final String SPORT_ID_PARAM = "sportID"
    public static final String MARKET_ID_PARAM = "marketID"

    public static final String SPORT_ID = "1"

    def grailsApplication

    def eventsService

    def updateAllOpenMarketsForEvents() {
        log.debug "Executing updateAllOpenMarketsForEvents"

        // automatic result retriever works for one day time
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"))
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        log.debug "Date factory timezone: " + calendar.getTimeZone()
        Date now = calendar.getTime()

        // go back two hours
        calendar.add(Calendar.HOUR, -10)
        Date twoHoursBack = calendar.getTime()

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH/mm")
        log.debug "From date is: " + simpleDateFormat.format(twoHoursBack)
        log.debug "To date is: " + simpleDateFormat.format(now)

        def events = eventsService.getAllEventsBetweenDatesWithUnsettledMarkets(twoHoursBack, now)
        log.debug "Number of events to process is: " + events?.size()

        for (Event event : events) {
            log.debug "Processing event " + event?.name
            for (Market market : event.markets) {
                try {
                    log.debug "Processing market: " + market?.marketName
                    if (!market?.settled) {
                        retrieveMarketResult(market)
                    } else {
                        log.debug "Market is settled."
                    }
                } catch (ex) {
                    log.error "Failed to update market from results rss feed.", ex
                }
            }
        }

        // if the for loop below executes more than five times with the same counted unsettled events then we
        // cancel the loop since something is wrong since the unsettled markets are getting lower
//        Integer withSameCounter = 0
//        Integer previousCounterValue = 0
//        Integer counter = eventsService.anyUnsettledMarketsInGivenDateRange(oneDayBack, now)
//        for (int i=0; i < counter ; i++) {
//
//            log.debug "Event remaining are: " + counter
//            def events = eventsService.getAllEventsBetweenDatesWithUnsettledMarkets(10, i, oneDayBack, now)
//
//            log.debug "Events being processed now: " + events?.size()
//            for (Event event : events) {
//                log.debug "Processing event " + event?.name
//                for (Market market : event.markets) {
//                    try {
//                        retrieveMarketResult(market)
//                    } catch (ex) {
//                        log.error "Failed to update market from results rss feed.", ex
//                    }
//                }
//            }
//
//            counter = eventsService.anyUnsettledMarketsInGivenDateRange(oneDayBack, now)
//
//            if (previousCounterValue == counter)
//                withSameCounter++
//            else {
//                previousCounterValue = counter
//                withSameCounter = 0
//            }
//
//            if (withSameCounter > 5) {
//                log.debug "Breaking loop since with same counter exceed five times rotation"
//            }
//        }
//
//        log.debug "Events process completed"
    }

    def retrieveMarketResult(Market market) {

        String format = grailsApplication.config.betfair.api.results.format
        String endpoint = grailsApplication.config.betfair.api.results.resultsEndpoint
        URIBuilder builder = new URIBuilder(endpoint).setQuery([ format: format, sportID: 1, marketID: market.getMarketIdClearedFromEventType() ])
        def rss = new XmlSlurper().parse(builder.toString())
        if (rss) {
            rss?.channel?.item?.each {
                ResultFeedEntry resultFeedEntry = ResultFeedEntry.createResultFeedEntry(market, it?.title?.toString())
                resultFeedEntry.winner = it?.description?.toString()?.trim()
                market.addToResultFeedEntries(resultFeedEntry)
                // attempt to find the winner if the market is settled
                if (resultFeedEntry.settled) {
                    for (Runner runner : market.runners) {
                        if (resultFeedEntry.winner && resultFeedEntry.winner?.toLowerCase()?.contains(runner?.runnerName?.toLowerCase())) {
                            market.winner = runner.selectionid
                            market.settled = Boolean.TRUE
                            market.settlementOn = new Date()

                            runner.winner = Boolean.TRUE

                            break
                        }
                    }
                }

                // persist any changes
                if (!market.save()) {
                    log.error "Failed to update market from result rss feed."
                    market.errors.each {
                        log.error it
                    }
                }
            }
        }
    }


}
