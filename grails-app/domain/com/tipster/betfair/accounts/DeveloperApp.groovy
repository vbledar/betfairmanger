package com.tipster.betfair.accounts

class DeveloperApp {

    String appName
    Long appId

    static hasMany = [appVersions: DeveloperAppVersion]

    static constraints = {
        appName nullable: true
        appId nullable: true
    }
}
