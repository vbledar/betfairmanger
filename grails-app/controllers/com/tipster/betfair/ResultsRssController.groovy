package com.tipster.betfair

import com.tipster.betfair.event.Market

class ResultsRssController {

    def resultsRssService

    def manageResultsRss() {
        render view: 'manageResultsRss'
    }

    def retrieveResultsRss() {
        log.error "Retrieving betfair results rss feed!"

        try {
            resultsRssService.updateAllOpenMarketsForEvents()
        } catch (ex) {
            log.error "Something went wrong.", ex
        }

        redirect action: 'manageResultsRss'
    }
}
