package com.tipster.betfair

import com.tipster.betfair.error.BetfairError

class ErrorsController {

    def viewErrors() {
        def errors = BetfairError.list(sort: 'id', order: 'desc')
        render view: 'errorsList', model: [errors: errors]
    }
}
