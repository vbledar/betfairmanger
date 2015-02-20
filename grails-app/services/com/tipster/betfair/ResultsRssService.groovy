package com.tipster.betfair

import com.tipster.betfair.event.Event
import com.tipster.betfair.event.Market
import com.tipster.betfair.event.ResultFeedEntry
import com.tipster.betfair.event.Runner
import grails.transaction.Transactional
import groovyx.net.http.URIBuilder

@Transactional
class ResultsRssService {

    public static final String FORMAT_PARAM = "format"
    public static final String SPORT_ID_PARAM = "sportID"
    public static final String MARKET_ID_PARAM = "marketID"

    public static final String SPORT_ID = "1"

    def grailsApplication

    def eventsService

    def updateAllOpenMarketsForEvents() {
        Date now = new Date()
        log.debug "Current date running is: " + now
        log.debug "Date to is: " + (now - 2)
        def events = eventsService.getAllEventsBetweenDatesWithUnsettledMarkets(now - 2, now)
        for (Event event : events) {
            log.error "Processing event."
            for (Market market : event.markets) {
                try {
                    retrieveMarketResult(market)
                } catch (ex) {
                    log.error "Failed to update market from results rss feed.", ex
                }
            }
        }
    }

    def retrieveMarketResult(Market market) {

        String format = grailsApplication.config.betfair.api.results.format
        String endpoint = grailsApplication.config.betfair.api.results.resultsEndpoint

        log.debug "Market id is: " + market.getMarketIdClearedFromEventType()

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
                        if (resultFeedEntry.winner && resultFeedEntry.winner.contains(runner.runnerName)) {
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
