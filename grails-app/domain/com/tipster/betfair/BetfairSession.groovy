package com.tipster.betfair

class BetfairSession {

    String session

    Date dateCreated

    static constraints = {
        session nullable: false
    }
}
