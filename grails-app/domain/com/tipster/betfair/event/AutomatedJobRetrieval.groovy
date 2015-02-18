package com.tipster.betfair.event

class AutomatedJobRetrieval {

    String serverName

    Boolean successful = Boolean.FALSE

    Boolean automatic = Boolean.FALSE

    Date dateCreated
    Date lastUpdated

    static constraints = {
        serverName nullable: true
        successful nullable: false
        automatic nullable: false
    }
}
