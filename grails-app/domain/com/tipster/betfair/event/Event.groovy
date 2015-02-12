package com.tipster.betfair.event

import com.tipster.betfair.Country

/**
 * Created by vbledar on 2/11/15.
 */
class Event {

    String id
    String name

    Country country

    String timezone

    Date openDate

    static belongsTo = [competition: Competition]

    static hasMany = [markets: Market]

    static constraints = {
        id nullable: false
        name nullable: false

        country nullable: true

        timezone nullable: true

        openDate nullable: false

        competition nullable: false

        markets nullable: true
    }

    static mapping = {
        id name: 'id', generator: 'assigned'
    }
}
