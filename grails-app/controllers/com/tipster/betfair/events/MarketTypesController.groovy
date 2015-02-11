package com.tipster.betfair.events

import com.tipster.BaseController
import com.tipster.betfair.Country
import com.tipster.betfair.event.Competition
import com.tipster.betfair.event.MarketType

class MarketTypesController extends BaseController {

    def marketTypesService

    def manageMarketTypes() {
        def marketTypes = MarketType.list()
        render view: 'manageMarketTypes', model: [marketTypes: marketTypes]
    }

    def retrieveBetfairMarketTypes() {
        try {
            log.info "Retrieving market types from BetFair."
            def marketTypes = marketTypesService.retrieveMarketTypesFromBetfair()
//            render template: 'persistedCompetitions', model: [competitions: competitions]
        } catch (ex) {
            log.error "Failed to retrieve market types from BetFair.", ex
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'market.types.management.retrieve.market.types.failed')]
            }
        }
    }

}
