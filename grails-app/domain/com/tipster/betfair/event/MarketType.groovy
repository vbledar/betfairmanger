package com.tipster.betfair.event

class MarketType {

    String name

    Boolean automaticRetrieval

    transient Boolean active

    static constraints = {
        name nullable: false
        automaticRetrieval nullable: true
        active nullable: true
    }

    static mapping = {
        id name: 'name', generator: 'assigned'
    }
}
