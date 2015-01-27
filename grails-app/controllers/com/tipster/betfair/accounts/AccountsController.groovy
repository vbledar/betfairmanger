package com.tipster.betfair.accounts

class AccountsController {

    def accountService

    def manageAccounts() {
//        def developerApps = DeveloperApp.findAll()
//        [developerApps: developerApps]
    }

    def retrieveBetfairAccounts() {
        try {
            accountService.retrieveApplicationKeys();

            render (contentType: 'application/json') {
                ['success': true, 'message': message(code: 'accounts.management.retrieved.accounts.successfully')]
            }
        } catch (ex) {
            render (contentType: 'application/json') {
                ['success': true, 'message': message(code: 'accounts.management.retrieve.accounts.failed')]
            }
        }


    }
}
