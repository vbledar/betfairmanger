package com.tipster.betfair.accounts

class DeveloperAppVersion {

    String owner

    Long versionId
    String version

    String applicationKey

    Boolean delayData
    Boolean subscriptionRequired
    Boolean ownerManaged

    Boolean active

    static belongsTo = [developerApp: DeveloperApp]

    static constraints = {
        owner nullable: true
        versionId nullable: true
        version nullable: true
        applicationKey nullable: true
        delayData nullable: true
        subscriptionRequired nullable: true
        ownerManaged nullable: true
        active nullable: true
        developerApp nullable: true
    }
}
