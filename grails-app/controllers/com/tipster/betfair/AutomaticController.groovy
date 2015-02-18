package com.tipster.betfair

class AutomaticController {

    def jobService

    def updateMarketsJob() {

        try {
            jobService.automaticSynchronizationWithBetfair(Boolean.TRUE)
        } catch (ex) {
            log.error "Something went wrong.", ex
        }

        // update countries session information
        def countries = Country.findAll()
        session["countries"] = countries

        render (contentType: 'application/json') {
            ['success': Boolean.TRUE]
        }

    }

}
