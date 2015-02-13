package com.tipster.betfair.event

class Runner {

    Long selectionid

    String runnerName

    Integer sortPriority

    Double runnerOdd

    static constraints = {
        selectionid nullable: false
        runnerName nullable: false
        sortPriority nullable: false

        runnerOdd nullable: true
    }
}
