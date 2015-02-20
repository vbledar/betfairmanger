package com.tipster.betfair

import com.tipster.betfair.event.Competition

class Country {

    String countryCode
    String countryName

    Boolean automaticRetrieval

    CountryInformation countryInformation

    Integer competitionsCounter = 0

    static belongsTo = [countryInformation: CountryInformation]

    static hasMany = [competitions: Competition]

    static mapping = {
        id name: 'countryCode', generator: 'assigned'
        cache true
    }

    static constraints = {
        countryCode nullable: false
        countryName nullable: true
        automaticRetrieval nullable: true
        countryInformation nullable: true
        competitionsCounter nullable: true
    }

    String getCountryName() {
        return countryName ? countryName : countryCode
    }

    String getCompetitionsCounted() {
        return competitionsCounter ? competitionsCounter : 0
    }
}
