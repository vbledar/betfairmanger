package com.tipster.betfair.accounts

class AccountsController {

    def accountService

    def manageAccounts() {
//        def developerApps = DeveloperApp.findAll()
//        [developerApps: developerApps]
    }

    def retrieveBetfairAccounts() {
        try {
            def developerApps = accountService.retrieveApplicationKeys();
            render template: 'accountsList', model: [developerApps: developerApps]
        } catch (ex) {
            log.error "Failed to retrieve accounts from betfair.", ex
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'accounts.management.retrieve.accounts.failed')]
            }
        }
    }
}
