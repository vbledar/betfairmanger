package com.tipster.betfair.error

class BetfairError {

    String code
    String message

    static hasOne = [betfairException: BetfairException]

    static constraints = {
        code nullable: true
        message nullable: true
        betfairException nullable: true
    }
}
