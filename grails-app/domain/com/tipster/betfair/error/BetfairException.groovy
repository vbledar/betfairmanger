package com.tipster.betfair.error

class BetfairException {

    static transient String ACCOUNT_API_NG_EXCEPTION = "AccountAPINGException"
    static transient String BETTING_API_NG_EXCEPTION = "BettingAPINGException"

    String errorCode
    String errorDetails
    String requestUUID

    String exceptionName

    static belongsTo = [betfairError: BetfairError]

    static constraints = {
        errorCode nullable: true
        errorDetails nullable: true
        requestUUID nullable: true

        exceptionName nullable: true

        betfairError nullable: false
    }

    static BetfairException createBetfairException(def betfairExceptionInformation) {
        if (betfairExceptionInformation.exceptionname) {
            String exceptionName = betfairExceptionInformation.exceptionname
            def exceptionInstance
            if (ACCOUNT_API_NG_EXCEPTION.equalsIgnoreCase(exceptionName))
                exceptionInstance = betfairExceptionInformation.AccountAPINGException

            String errorCode = exceptionInstance.errorCode
            String errorDetails = exceptionInstance.errorDetails
            String requestUUID = exceptionInstance.requestUUID

            return new BetfairException(errorCode: errorCode, errorDetails: errorDetails, requestUUID: requestUUID, exceptionName: exceptionName)
        }

        return null
    }
}
