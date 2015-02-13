package com.tipster.betfair.event

class MarketType {

    String name

    transient Boolean active

    static constraints = {
        name nullable: false
        active nullable: true
    }

    static mapping = {
        id name: 'name', generator: 'assigned'
    }
}
