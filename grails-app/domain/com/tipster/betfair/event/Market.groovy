package com.tipster.betfair.event

class Market {

    static belongsTo = [event: Event, marketType: MarketType]


    static constraints = {
    }
}
