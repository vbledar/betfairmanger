package com.tipster.betfair.event

class Team {

    String name

    static belongsTo = [competition: Competition]

    static constraints = {
        name nullable: false
        competition nullable: false
    }
}
