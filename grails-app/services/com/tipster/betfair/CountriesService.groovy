package com.tipster.betfair

import grails.transaction.Transactional

@Transactional
class CountriesService extends BaseService {

    def updateCountryAutomaticRetrievalState(Country country) {
        country.automaticRetrieval = !country.automaticRetrieval
        if (!country.save()) {
            log.error "Could not update country's automatic retrieval state."
            country.errors.each {
                log.error it
            }
            return false
        }
        return true
    }

}
