package com.tipster.betfair

class Country {

    String countryCode

    static mapping = {
        id name: 'countryCode', generator: 'assigned'
    }

    static constraints = {
    }

    String getCountryName() {
        String[] isoCountries = Locale.getISOCountries()
        for (String isoCountry : isoCountries) {
            if (isoCountry.equalsIgnoreCase(this.countryCode)) {
                Locale locale = new Locale(this.countryCode)
                return locale.getDisplayCountry()
            }
        }
    }
}
