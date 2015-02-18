package com.tipster.betfair

class AutomaticController {

    def jobService

    def updateMarketsJob() {

        jobService.automaticSynchronizationWithBetfair(Boolean.TRUE)

        render (contentType: 'application/json') {
            ['success': Boolean.TRUE]
        }

    }

}
