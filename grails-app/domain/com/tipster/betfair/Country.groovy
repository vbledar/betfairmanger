package com.tipster.betfair

class Country {

    String countryCode

    CountryInformation countryInformation

    static belongsTo = [countryInformation: CountryInformation]

    static mapping = {
        id name: 'countryCode', generator: 'assigned'
        cache true
        countryInformation cache: true
    }

    static fetchMode = [countryInformation: 'eager']

    static constraints = {
        countryCode nullable: false
        countryInformation nullable: true
    }

    String getCountryName() {
        return countryInformation ? countryInformation?.name : countryCode
    }
}
