package com.tipster.betfair

class AutomaticController {

    def jobService

    def updateMarketsJob() {

        jobService.automaticSynchronizationWithBetfair(Boolean.TRUE)

        // update countries session information
        def countries = Country.findAll()
        session["countries"] = countries

        render (contentType: 'application/json') {
            ['success': Boolean.TRUE]
        }

    }

}
