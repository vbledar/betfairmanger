package betfairmanger

import com.tipster.betfair.Country
import com.tipster.betfair.CountryInformation

class DataInformationFilters {

    def filters = {
        all(controller:'*', action:'*') {
            before = {
                // store countries and countries information in session
                if (!session["countriesInformation"])
                    session["countriesInformation"] = CountryInformation.list()
                if (!session["countries"])
                    session["countries"] = Country.list()
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
