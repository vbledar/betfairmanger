package com.tipster.betfair.exceptions

import com.tipster.betfair.error.BetfairError

/**
 * Created by vbledar on 2/10/15.
 */
class BetfairWrapperException extends Exception {

    BetfairError betfairError

    public String getErrorDescription() {
        StringBuilder builder = new StringBuilder()
        if (betfairError?.betfairException) {
            if (betfairError?.betfairException?.errorCode) builder.append("Error code: " + betfairError?.betfairException?.errorCode + "\n")
            if (betfairError?.betfairException?.errorDetails) builder.append("Error details: " + betfairError?.betfairException?.errorDetails + "\n")
            if (betfairError?.betfairException?.requestUUID) builder.append("Request UUID: " + betfairError?.betfairException?.requestUUID + "\n")
            return builder.toString()
        }
        if (betfairError?.code) builder.append("Error code: " + betfairError?.code + "\n")
        if (betfairError?.message) builder.append("Error message: " + betfairError?.message + "\n")
        return builder.toString()
    }

}
