package com.tipster.betfair.event

import com.tipster.betfair.Country

class Competition {

    String competitionId
    String competitionName

    Boolean automaticRetrieval

    Country country

    transient Integer eventsCounter

    static hasMany = [events: Event, teams: Team]

    static constraints = {
        competitionId nullable: false, blank: false
        competitionName nullable: false, blank: false

        automaticRetrieval nullable: true

        country nullable: true

        eventsCounter nullable: true
    }

    static mapping = {
        id name: 'competitionId', generator: 'assigned'
    }
}
