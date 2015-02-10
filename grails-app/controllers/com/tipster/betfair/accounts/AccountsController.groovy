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
                ['success': false, 'message': message(code: ex ? ex.getMessage() : 'accounts.management.retrieve.accounts.failed')]
            }
        }
    }

    def persistBetfairAccounts() {
        try {
            log.info "Persisting accounts from betfair."
            def developerApps = accountService.retrieveApplicationKeys(Boolean.TRUE);
            render template: 'accountsList', model: [developerApps: developerApps, showActions: Boolean.TRUE]
        } catch(ex) {
            log.error "Failed to persist accounts from betfair.", ex
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'accounts.management.retrieve.accounts.failed')]
            }
        }
    }

    def deleteAccount() {
        DeveloperAppVersion developerAppVersion = DeveloperAppVersion.get(params.elementId)
        if (!developerAppVersion) {
            render (contentType: 'application/json') {
                ['success': false, 'message': message(code: 'accounts.management.delete.account.not.found')]
            }
            return
        }

        if (accountService.deleteAccount(developerAppVersion)) {
            render (contentType: 'application/json') {
                ['success': true, 'message': message(code: 'accounts.management.delete.account.successful')]
            }
            return
        }

        render (contentType: 'application/json') {
            ['success': false, 'message': message(code: 'accounts.management.delete.account.failed')]
        }
        return
    }
}
