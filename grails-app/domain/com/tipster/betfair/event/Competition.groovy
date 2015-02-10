package com.tipster.betfair.event

import com.tipster.betfair.Country

class Competition {

    String competitionId
    String competitionName

    Country country

    static constraints = {
        competitionId nullable: false, blank: false
        competitionName nullable: false, blank: false

        country nullable: true
    }

    static mapping = {
        id name: 'competitionId', generator: 'assigned'
    }
}
