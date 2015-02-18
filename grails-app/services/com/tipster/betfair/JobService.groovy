package com.tipster.betfair

import com.tipster.betfair.event.AutomatedJobRetrieval
import com.tipster.betfair.event.Competition
import com.tipster.betfair.event.MarketType
import grails.transaction.Transactional

@Transactional
class JobService {

    def competitionsService
    def eventsService

    def automaticSynchronizationWithBetfair(Boolean automatic) {

        AutomatedJobRetrieval automatedJobRetrieval

        try {
            automatedJobRetrieval = new AutomatedJobRetrieval(automatic: automatic)

            log.info "Updating competitions based on countries."
            try {
                def countries = Country.findByAutomaticRetrieval(Boolean.TRUE)
                countries.each {
                    competitionsService.retrieveCompetitionsFromBetfairForCountry(it)
                }
                log.info "Update of competitions based on countries was successful."
            } catch (ex) {
                log.error "Failed to synchronize competitions on defined countries.", ex
            }

            log.info "Updating events based on competitions."
            try {
                def competitions = Competition.findByAutomaticRetrieval(Boolean.TRUE)
                competitions.each {
                    eventsService.synchronizeEventsFromBetfair(it)
                }
            } catch (ex) {
                log.error "Failed to update events based on competitions.", ex
            }

            log.info "Updating markets based on market types and competitions."
            try {
                def marketTypes = MarketType.findByAutomaticRetrieval(Boolean.TRUE)
                def competitions = Competition.findByAutomaticRetrieval(Boolean.TRUE)
                competitions.each {
                    it.events.each {
                        eventsService.synchronizeEventMarketsFromBetfair(it, marketTypes)
                    }
                }
            } catch (ex) {
                log.error "Failed to update markets based on market types and competitions."
            }

            log.info "Updating market odds based on markets."
            try {
                def competitions = Competition.findByAutomaticRetrieval(Boolean.TRUE)
                competitions.each {
                    it.events.each {
                        it.markets.each {
                            eventsService.synchronizeEventMarketOddsFromBetfair(it)
                        }
                    }
                }
            } catch (ex) {
                log.error "Failed to update market odds based on markets."
            }

            automatedJobRetrieval.successful = Boolean.TRUE
        } catch(ex) {
            log.error "Automatic job execution failed.", ex
        }

        if (automatedJobRetrieval) {
            if (automatedJobRetrieval.save()) {
                log.error "Failed to persist automated job retrieval monitoring record"
                automatedJobRetrieval.errors.each {
                    log.error it
                }
            }
        }
    }

}
