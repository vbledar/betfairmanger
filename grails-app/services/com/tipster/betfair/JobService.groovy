package com.tipster.betfair

import com.tipster.betfair.event.AutomatedJobRetrieval
import com.tipster.betfair.event.Competition
import com.tipster.betfair.event.MarketType

class JobService {

    def competitionsService
    def eventsService

    def automaticSynchronizationWithBetfair(Boolean automatic) {

        AutomatedJobRetrieval automatedJobRetrieval

        try {
            automatedJobRetrieval = new AutomatedJobRetrieval(automatic: automatic)

            log.info "Updating competitions based on countries."
            try {
                def countries = Country.findAllByAutomaticRetrieval(Boolean.TRUE)

                log.info "About to update competitions for " + countries.size() + " countries."
                countries.each {
                    log.info "Updating competitions for country " + it.countryName

                    try {
                        competitionsService.retrieveCompetitionsFromBetfairForCountry(it)
                    } catch (ex) {
                        log.error "Failed to update competitions for country " + it.countryName
                    }
                }
                log.info "Update of competitions based on countries was successful."
            } catch (ex) {
                log.error "Failed to synchronize competitions on defined countries.", ex
            }

            log.info "Updating events based on competitions."
            try {
                def competitions = Competition.findAllByAutomaticRetrieval(Boolean.TRUE)
                competitions.each {
                    eventsService.synchronizeEventsFromBetfair(it)
                }
            } catch (ex) {
                log.error "Failed to update events based on competitions.", ex
            }

            log.info "Updating markets based on market types and competitions."
            try {
                def marketTypes = MarketType.findAllByAutomaticRetrieval(Boolean.TRUE)
                def competitions = Competition.findAllByAutomaticRetrieval(Boolean.TRUE)
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
                def competitions = Competition.findAllByAutomaticRetrieval(Boolean.TRUE)
                competitions.each {
                    log.info "Processing competition " + it.competitionName
                    it.events.each {
                        log.info "Processing event " + it.name
                        it.markets.each {
                            log.info "Processing market " + it.marketName
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

//        if (automatedJobRetrieval) {
//            if (!automatedJobRetrieval.save()) {
//                log.error "Failed to persist automated job retrieval monitoring record"
//                automatedJobRetrieval.errors.each {
//                    log.error it
//                }
//            }
//        }
    }

}
