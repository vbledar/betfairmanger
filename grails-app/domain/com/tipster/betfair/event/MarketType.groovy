package com.tipster.betfair.event

class MarketType {

    String name

    static constraints = {
        name nullable: false
    }

    static mapping = {
        id name: 'name', generator: 'assigned'
    }
}
