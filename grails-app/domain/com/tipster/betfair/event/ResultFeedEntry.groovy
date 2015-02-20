package com.tipster.betfair.event
/**
 * Created by vbledar on 2/19/15.
 */
class ResultFeedEntry {

    String data                                 // the data retrieved from the feed

    String date                                 // the parsed fixture date from feed
    String time                                 // the parsed time from feed

    String game                                 // the parsed game from feed

    String marketName                           // the parsed market name from feed

    Boolean settled                             // defines if settled exists in feed

    String winner                               // describes the winner (meaning runner in market)

    Date dateCreated                            // the date that the record was created
    Date lastUpdated                            // the date that the record was last updated

    static belongsTo = [market: Market]         // the market for which this feed is retrieved

    static constraints = {
        data nullable: true
        date nullable: true
        time nullable: true
        game nullable: true
        marketName nullable: true
    }

    public String toString() {
        return this.game + " | " + this.marketName + " | " + this.date + " " + this.time + " | " + this.winner + " | " + settled
    }

    public static ResultFeedEntry createResultFeedEntry(Market market, String entry) {
        ResultFeedEntry resultFeedEntry = new ResultFeedEntry()

        if (!entry) {
            throw new Exception("The given entry is empty");
        }

        int dashIndex = -1
        int settledIndex = -1
        int slashIndex = entry.indexOf(" / ")
        if (slashIndex > -1) {
            resultFeedEntry.date = entry.substring(0, slashIndex).trim()
            dashIndex = entry.indexOf(" - ")
            if (dashIndex > -1) {
                resultFeedEntry.game = entry.substring(slashIndex+3, dashIndex).trim()
                resultFeedEntry.time = entry.substring(dashIndex+3, dashIndex+8).trim()

                settledIndex = entry.indexOf("settled")
                if (settledIndex > -1) {
                    resultFeedEntry.marketName = entry.substring(dashIndex + 9, settledIndex).trim()
                    resultFeedEntry.settled = Boolean.TRUE
                } else {
                    resultFeedEntry.marketName = entry.substring(dashIndex + 9).trim()
                }
            }
        }

        if (slashIndex == -1 || dashIndex == -1) {
            throw new Exception("RSS feed for market " + market?.marketName + " with id " + market?.marketId + "is not valid.")
        }

        return resultFeedEntry
    }
}
