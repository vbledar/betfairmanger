package com.tipster.betfair.betting

import com.tipster.betfair.BaseService
import com.tipster.betfair.BetfairApiService
import com.tipster.betfair.Country
import com.tipster.betfair.CountryInformation
import com.tipster.betfair.MarketFilter
import com.tipster.betfair.PriceProjection
import com.tipster.betfair.enums.MarketProjection
import com.tipster.betfair.enums.PriceData
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

        for (def countryCode : jsonResponse?.result) {
            iso2LetterCode = countryCode.countryCode

            if (iso2LetterCode) {
                country = Country.findByCountryCode(iso2LetterCode)

                // attempt to find country information by using the 2 letter iso code
                countryInformation = CountryInformation.findByIso2LetterCode(iso2LetterCode)

                if (country) {
                    country.countryInformation = countryInformation
                    country.countryName = countryInformation?.name
                } else {
                    // create a new country instance
                    country = new Country(countryCode: countryCode.countryCode, countryName: countryInformation?.name, countryInformation: countryInformation)
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
        for (def record : jsonResponse?.result) {
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

    def synchronizeEventMarketsFromBetfair(Event event, def marketTypes) {
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
        for (def record : jsonResponse?.result) {
            LazyMap marketInformation = (LazyMap) record

            market = Market.findByMarketId(marketInformation.marketId)
            if (!market) {
                market = new Market()
                market.marketId = marketInformation.marketId
                market.marketName = marketInformation.marketName
                market.marketType = marketType
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

    def synchronizeEventMarketOddsFromBetfair(Market market) {
        String api = grailsApplication.config.betfair.api.betting.bettingApi
        String apiVersion = grailsApplication.config.betfair.api.betting.bettingApiVersion
        String action = grailsApplication.config.betfair.api.betting.actionListMarketBook

        MarketFilter marketFilter = new MarketFilter()
        marketFilter.addMarketId(market.marketId)

        PriceProjection priceProjection = new PriceProjection()
        priceProjection.addPriceData(PriceData.EX_BEST_OFFERS)

        Map<String, Object> params = new HashMap<>()
        params.put("marketIds", marketFilter.getMarketIds())
        params.put("priceProjection", priceProjection)

        JsonRpcRequest rpcRequest = new JsonRpcRequest()
        rpcRequest.id = BetfairApiService.BETFAIR_API_ID
        rpcRequest.jsonrpc = BetfairApiService.BETFAIR_JSON_RPC_VERSION
        rpcRequest.method = api + apiVersion + action
        rpcRequest.params = params

        def jsonResponse = betfairApiService.executeBetfairApiCall(rpcRequest)

        for (def record : jsonResponse?.result) {
            LazyMap marketInformation = (LazyMap) record
            if (marketInformation.containsKey("runners")) {
                for (def runnerRecord : marketInformation.get("runners")) {
                    LazyMap runnerInformation = (LazyMap) runnerRecord
                    for (Runner runner : market.runners) {
                        String runnerId = runner?.selectionid
                        String runnerIdToCheck = runnerInformation.get("selectionId")
                        if (runnerId && runnerIdToCheck && runnerId.equalsIgnoreCase(runnerIdToCheck)) {
                            LazyMap runnerOdds = runnerInformation.get("ex")
                            try {
                                String runnerOdd = ((LazyMap) runnerOdds?.get("availableToBack")[0])?.get("price")
                                log.debug "Runner odd is: " + runnerOdd
                                runner.runnerOdd = Double.parseDouble runnerOdd

                                if (!runner.save()) {
                                    log.error "Failed to persist runner [" + runner?.selectionid + "] with updated odds."
                                    runner.errors.each {
                                        log.error it
                                    }
                                }
                            } catch(ex) {
                                log.error "An unknown error has occured while updating runner odds.", ex
                            }

                            break
                        }
                    }
                }
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

    def getAllEventsBetweenDatesWithUnsettledMarkets(Date dateFrom, Date dateTo) {
        def criteria = Event.createCriteria()
        def results = criteria.listDistinct {
            between ('openDate', dateFrom, dateTo)
            markets {
                or {
                    eq('settled', Boolean.FALSE)
                    isNull('settled')
                }
            }
        }
    }

    Integer anyUnsettledMarketsInGivenDateRange(Date dateFrom, Date dateTo) {
        def criteria = Event.createCriteria()
        def counter = criteria.get {

            between ('openDate', dateFrom, dateTo)
            markets {
                or {
                    eq('settled', Boolean.FALSE)
                    isNull('settled')
                }
            }

            projections {
                countDistinct('id')
            }
        }
    }
}
