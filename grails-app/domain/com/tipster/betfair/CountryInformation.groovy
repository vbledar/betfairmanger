package com.tipster.betfair

class CountryInformation {

    String name
    String formalName

    String capital

    String currency
    String currencyName

    String telephoneCode

    String iso1NumberCode
    String iso2LetterCode
    String iso3LetterCode

    static hasOne = [country: Country]

    static constraints = {
        name nullable: false
        formalName nullable: true

        capital nullable: true

        currency nullable: true
        currencyName nullable: true

        telephoneCode nullable: true

        iso1NumberCode nullable: true
        iso2LetterCode nullable: true
        iso3LetterCode nullable: true

        country nullable: true
    }

    static mapping = {
        cache true
    }
}
