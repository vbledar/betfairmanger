import com.tipster.betfair.CountryInformation

class BootStrap {

    def init = { servletContext ->
        importCountryInformation()
    }
    def destroy = {
    }

    def importCountryInformation() {

        // load all country information records so that they get cached in the system
        CountryInformation.findAll()


        // process countries file if one exists
        BufferedReader bufferedReader
        try {
            File countriesFile = new File("/Users/vbledar/countries.csv")
            if (countriesFile.exists()) {
                bufferedReader = new BufferedReader(new FileReader(countriesFile))
            } else {
                log.debug "Countries file doesn't exist."
                log.debug "Import of countries information is aborted."
                return
            }

            int counter = 0
            String line
            CountryInformation countryInformation
            while((line = bufferedReader.readLine()) != null) {
//                log.debug "Line read is: " + line
                if (counter == 0) {
                    counter++
                    continue
                }

                String[] countryLine = line.split(",")
                countryInformation = CountryInformation.findByIso3LetterCode(countryLine[11])
                if (!countryInformation) {
                    countryInformation = new CountryInformation()
                    countryInformation.id = Long.parseLong(countryLine[0])
                }

                countryInformation.name = countryLine[1]
                countryInformation.formalName = countryLine[2]
                countryInformation.capital = countryLine[6]
                countryInformation.currency = countryLine[7]
                countryInformation.currencyName = countryLine[8]
                countryInformation.telephoneCode = countryLine[9]
                countryInformation.iso1NumberCode = countryLine[12]
                countryInformation.iso2LetterCode = countryLine[10]
                countryInformation.iso3LetterCode = countryLine[11]

                if (!countryInformation.save()) {
                    log.error "Failed to persist/update country information..."
                    countryInformation.errors.each {
                        log.error it
                    }
                }
            }
        } catch(Exception ex) {
            log.debug "A problem occurred while processing the countries import file"
            log.error ex
        }
    }
}
