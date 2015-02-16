package com.tipster.betfair

import com.tipster.betfair.event.Competition

class Country {

    String countryCode

    Boolean automaticRetrieval

    CountryInformation countryInformation

    static belongsTo = [countryInformation: CountryInformation]

    static hasMany = [competitions: Competition]

    static mapping = {
        id name: 'countryCode', generator: 'assigned'
        cache true
        countryInformation cache: true
    }

    static fetchMode = [countryInformation: 'eager']

    static constraints = {
        countryCode nullable: false
        automaticRetrieval nullable: true
        countryInformation nullable: true
    }

    String getCountryName() {
        return countryInformation ? countryInformation?.name : countryCode
    }

    String getCompetitionsCounted() {
        def size = Competition.countByCountry(this)
    }
}
