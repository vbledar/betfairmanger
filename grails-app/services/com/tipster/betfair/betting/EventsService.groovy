package com.tipster.betfair.betting

import com.tipster.betfair.BaseService
import com.tipster.betfair.BetfairApiService
import com.tipster.betfair.Country
import com.tipster.betfair.CountryInformation
import com.tipster.betfair.MarketFilter
import com.tipster.betfair.enums.MarketProjection
import com.tipster.betfair.error.BetfairError
import com.tipster.betfair.event.Competition
import com.tipster.betfair.event.Event
import com.tipster.betfair.event.Market
import com.tipster.betfair.event.MarketType
import com.tipster.betfair.event.Runner
import com.tipster.betfair.exceptions.BetfairWrapperException
import com.tipster.betfair.util.json.JsonConverter
import com.tipster.betfair.utils.http.JsonRpcRequest
import grails.transaction.Transactional
import groovy.json.internal.LazyMap
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Transactional
class EventsService extends BaseService {


    def betfairApiService

    def synchronizeCountriesFromBetfair() {
        String api = grailsApplication.config.betfair.api.betting.bettingApi
        String apiVersion = grailsApplication.config.betfair.api.betting.bettingApiVersion
        String action = grailsApplication.config.betfair.api.betting.actionListCountries

        MarketFilter marketFilter = new MarketFilter()
        marketFilter.addExchangeId("1")
        Map<String, Object> params = new HashMap<>()
        params.put("filter", marketFilter)

        JsonRpcRequest rpcRequest = new JsonRpcRequest()
        rpcRequest.id = BetfairApiService.BETFAIR_API_ID
        rpcRequest.jsonrpc = BetfairApiService.BETFAIR_JSON_RPC_VERSION
        rpcRequest.method = api + apiVersion + action
        rpcRequest.params = params

        def jsonResponse = betfairApiService.executeBetfairApiCall(rpcRequest)

        String iso2LetterCode
        Country country
        CountryInformation countryInformation

        for (def countryCode : jsonResponse.result) {
            iso2LetterCode = countryCode.countryCode

            if (iso2LetterCode) {
                country = Country.findByCountryCode(iso2LetterCode)

                // attempt to find country information by using the 2 letter iso code
                countryInformation = CountryInformation.findByIso2LetterCode(iso2LetterCode)

                if (country) {
                    country.countryInformation = countryInformation
                } else {
                    // create a new country instance
                    country = new Country(countryCode: countryCode.countryCode, countryInformation: countryInformation)
                }

                try {
                    if (!country.save()) {
                        log.error "Failed to persist country by ISO-2-Letter code [" + iso2LetterCode + "]."
                        country.errors.each {
                            log.error it
                        }
                    }
                } catch (ex) {
                    log.error "An unknown exception occurred while trying to persist a new country instance by ISO-2-Letter code [" + iso2LetterCode + "].", ex
                }
            }
        }
    }

    def synchronizeEventsFromBetfair(Competition competition) {
        String api = grailsApplication.config.betfair.api.betting.bettingApi
        String apiVersion = grailsApplication.config.betfair.api.betting.bettingApiVersion
        String action = grailsApplication.config.betfair.api.betting.actionListEvents

        MarketFilter marketFilter = new MarketFilter()
        marketFilter.addExchangeId("1")
        marketFilter.addCompetitionId(competition.competitionId)
        Map<String, Object> params = new HashMap<>()
        params.put("filter", marketFilter)

        JsonRpcRequest rpcRequest = new JsonRpcRequest()
        rpcRequest.id = BetfairApiService.BETFAIR_API_ID
        rpcRequest.jsonrpc = BetfairApiService.BETFAIR_JSON_RPC_VERSION
        rpcRequest.method = api + apiVersion + action
        rpcRequest.params = params

        def jsonResponse = betfairApiService.executeBetfairApiCall(rpcRequest)

        Event event
        for (def record : jsonResponse.result) {
            LazyMap eventInformation = (LazyMap) record.event

            event = Event.findById(eventInformation.id)
            if (!event) {
                log.debug "Event not found in database so I'm creating a new instance with id [" + eventInformation.id + "]."
                event = new Event()
                event.id = eventInformation.id
                event.competition = competition
            }

            event.name = eventInformation.name
            event.country = competition.country
            event.timezone = eventInformation.timezone
            event.openDate = javax.xml.bind.DatatypeConverter.parseDateTime(eventInformation.openDate).getTime()

            try {
                if (!event.save()) {
                    log.error "Failed to persist event by id [" + event.id + "] and name [" + event.name + "]."
                    event.errors.each {
                        log.error it
                    }
                }
            } catch(ex) {
                log.error "An unknown exception occured while trying to perist a new event instance by id [" + eventInformation.id + "] and name [" + eventInformation.name + "]."
            }
        }
    }

    def synchronizeEventMarketsFromBetfair(Event event, Set<MarketType> marketTypes) {
        Boolean hasSuccess = Boolean.FALSE
        Boolean hasException = Boolean.FALSE
        for (MarketType marketType : marketTypes) {
            try {
                synchronizeEventMarketFromBetfair(event, marketType)
                hasSuccess = Boolean.TRUE
            } catch(ex) {
                log.error "Exception caught while attempting to synchronize event markets from betfair.", ex
                hasException = Boolean.TRUE
            }
        }
    }

    def synchronizeEventMarketFromBetfair(Event event, MarketType marketType) {
        String api = grailsApplication.config.betfair.api.betting.bettingApi
        String apiVersion = grailsApplication.config.betfair.api.betting.bettingApiVersion
        String action = grailsApplication.config.betfair.api.betting.actionListMarketCatalogue

        MarketFilter marketFilter = new MarketFilter()
        marketFilter.addExchangeId("1")
        marketFilter.addCompetitionId(event.competition.competitionId)
        marketFilter.addMarketCountry(event.country.countryCode)
        marketFilter.addEventId(event.id)
        marketFilter.addMarketTypeCode(marketType.name)

        Set<MarketProjection> marketProjections = new HashSet<>()
        marketProjections.add(MarketProjection.RUNNER_DESCRIPTION)

        Map<String, Object> params = new HashMap<>()
        params.put("filter", marketFilter)
        params.put("marketProjection", marketProjections)
        params.put("maxResults", 1)

        JsonRpcRequest rpcRequest = new JsonRpcRequest()
        rpcRequest.id = BetfairApiService.BETFAIR_API_ID
        rpcRequest.jsonrpc = BetfairApiService.BETFAIR_JSON_RPC_VERSION
        rpcRequest.method = api + apiVersion + action
        rpcRequest.params = params

        def jsonResponse = betfairApiService.executeBetfairApiCall(rpcRequest)

        Market market
        Runner runner
        for (def record : jsonResponse.result) {
            LazyMap marketInformation = (LazyMap) record

            market = Market.findByMarketId(marketInformation.marketId)
            if (!market) {
                market = new Market()
                market.marketId = marketInformation.marketId
                market.marketName = marketInformation.marketName
                market.event = event

                for (def runnerRecord : marketInformation.runners) {
                    LazyMap runnerInformation = (LazyMap) runnerRecord
                    runner = new Runner()
                    runner.selectionid = runnerInformation.selectionId
                    runner.runnerName = runnerInformation.runnerName
                    runner.sortPriority = runnerInformation.sortPriority
                    market.addToRunners(runner)
                }
            }

            try {
                if (!market.save()) {
                    log.error "Failed to persist market by id [" + market.marketId + "] and name [" + market.marketName + "]."
                    market.errors.each {
                        log.error it
                    }
                }
            } catch(ex) {
                log.error "An unknown exception occured while trying to persist a new market instance by id [" + marketInformation.marketId + "] and name [" + marketInformation.marketName + "]."
            }
        }
    }

    def getEventsByCompetition(Competition competition) {
        def criteria = Event.createCriteria()
        def results = criteria.list {
            eq ('competition', competition)
        }
    }

    def getMarketsByEvent(Event event) {
        def criteria = Market.createCriteria()
        def results = criteria.list {
            eq ('event', event)
        }
    }
}
