package com.tipster.betfair

class Country {

    String countryCode

    static mapping = {
        id name: 'countryCode', generator: 'assigned'
    }

    static constraints = {
    }

    String getCountryName() {
        CountryInformation countryInformation = CountryInformation.findByIso2LetterCode(this.countryCode)
        if (countryInformation) return countryInformation?.name
    }
}
