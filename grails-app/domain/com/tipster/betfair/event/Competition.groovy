package com.tipster.betfair.event

class Competition {

    String competitionId
    String competitionName

    static constraints = {
        competitionId nullable: false
        competitionName nullable: false
    }

    static mapping = {
        id name: 'competitionId', generator: 'assigned'
    }
}
