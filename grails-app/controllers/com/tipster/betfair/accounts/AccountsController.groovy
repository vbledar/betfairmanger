package com.tipster.betfair.accounts

class AccountsController {

    def accountService

    def manageAccounts() {
        def developerApps = DeveloperApp.findAll()
        render(view: 'manageAccounts', model: [developerApps: developerApps])
    }

    def retrieveBetfairAccounts() {
        try {
            log.info "Retrieving account from betfair."
            def developerApps = accountService.retrieveApplicationKeys(Boolean.FALSE);
            render template: 'accountsList', model: [developerApps: developerApps]
        } catch (ex) {
            log.error "Failed to retrieve accounts from betfair.", ex
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'accounts.management.retrieve.accounts.failed')]
            }
        }
    }

    def persistBetfairAccounts() {
        try {
            log.info "Persisting accounts from betfair."
            def developerApps = accountService.retrieveApplicationKeys(Boolean.TRUE);
            render template: 'accountsList', model: [developerApps: developerApps]
        } catch(ex) {
            log.error "Failed to persist accounts from betfair.", ex
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'accounts.management.retrieve.accounts.failed')]
            }
        }
    }
}
