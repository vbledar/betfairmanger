package com.tipster.betfair

class Country {

    String countryCode

    static belongsTo = [countryInformation: CountryInformation]

    static mapping = {
        id name: 'countryCode', generator: 'assigned'
    }

    static constraints = {
        countryCode nullable: false
        countryInformation nullable: true
    }

    String getCountryName() {
        return countryInformation?.name
    }
}
