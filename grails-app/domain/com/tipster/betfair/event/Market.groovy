package com.tipster.betfair.event

class Market {

    String marketId
    String marketName

    static belongsTo = [event: Event, marketType: MarketType]

    static hasMany = [runners : Runner]

    static mapping = {
        id name: 'marketId', generator: 'assigned'
    }

    static constraints = {
        event nullable: false
        marketType nullable: false
        runners nullable: true
    }
}
