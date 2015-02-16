package com.tipster.betfair.events

import com.tipster.BaseController
import com.tipster.betfair.Country
import com.tipster.betfair.event.Competition
import com.tipster.betfair.event.MarketType

class MarketTypesController extends BaseController {

    def marketTypesService

    def manageMarketTypes() {
        if (!params.max) params.max = 15
        if (!params.offset) params.offset = 0

        def marketTypes = MarketType.list(params)
        render view: 'manageMarketTypes', model: [marketTypes: marketTypes]
    }

    def manageMarketTypesFiltered() {
        def marketTypes = MarketType.list(params)
        render template: 'persistedMarketTypesFiltered', model: [marketTypes: marketTypes]
    }

    def setMarketTypeAutomaticRetrieval() {
        MarketType marketType = MarketType.findByName(params.marketTypeName)
        if (!marketType) {
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'market.types.management.market.type.not.found.by.name', args: [params.marketTypeName])]
            }
            return
        }

        if (!marketTypesService.updateMarketTypeAutomaticRetrievalState(marketType)) {
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'market.types.management.market.type.not.updated', args: [params.marketTypeName])]
            }
        }

        render (contentType: 'application/json') {
            ['success': true, 'message': message(code: 'market.types.management.market.type.updated.successfully', args: [params.marketTypeName])]
        }

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
