package com.tipster.betfair.event

class Market {

    String marketId                                                 // market id (holds exchange id as well)
    String marketName                                               // market name

    Boolean settled                                                 // defines if this market is settled
    Date settlementOn                                               // defines the date the market was settled on

    Long winner                                                     // this value is set to the runner selectionId that
                                                                    // wins in the market

    Date dateCreated                                                // the date on which the record was created
    Date lastUpdated                                                // the date on which the record was last updated

    static belongsTo = [event: Event, marketType: MarketType]       // every market has an event and a market type

    static hasMany = [runners : Runner, resultFeedEntries: ResultFeedEntry] // list of runners in this market
                                                                            // list of rss feed entries (usually one)

    static mapping = {
        id name: 'marketId', generator: 'assigned'
    }

    static constraints = {
        marketId nullable: false
        marketName nullable: false

        settled nullable: true
        settlementOn nullable: true

        winner nullable: true

        event nullable: false
        marketType nullable: false

        runners nullable: true

        resultFeedEntries nullable: true
    }

    public String getMarketIdClearedFromEventType() {
        int index = marketId.indexOf(".")
        if (index > -1) return marketId.substring(index+1)
    }
}
