package com.tipster.betfair.event

class EventType {

    String eventId
    String name

    Boolean processed = Boolean.FALSE

    static constraints = {
        name nullable: false
        processed nullable: true
    }
}
