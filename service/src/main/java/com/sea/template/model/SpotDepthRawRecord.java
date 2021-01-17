package com.sea.template.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sea.template.lang.RawRecordStatus;
import org.joda.time.DateTime;

import java.util.List;

public class SpotDepthRawRecord {

    @JsonProperty("asks")
    private final List<BidAsk> asks;

    @JsonProperty("bids")
    private final List<BidAsk> bids;

    @JsonProperty("timestamp")
    private final DateTime timestamp;

    private SpotDepthRawRecord(List<BidAsk> asks, List<BidAsk> bids, DateTime timestamp) {
        this.asks = asks;
        this.bids = bids;
        this.timestamp = timestamp;
    }

    public List<BidAsk> getAsks() {
        return asks;
    }

    public List<BidAsk> getBids() {
        return bids;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<BidAsk> asks;
        private List<BidAsk> bids;
        private DateTime timestamp;

        public Builder asks(List<BidAsk> asks){
            this.asks = asks;
            return this;
        }

        public Builder bids(List<BidAsk> bids){
            this.bids = bids;
            return this;
        }

        public Builder timestamp(String timestamp){
            this.timestamp = DateTime.parse(timestamp);
            return this;
        }

        public SpotDepthRawRecord build() {
            return new SpotDepthRawRecord(this.asks, this.bids, this.timestamp);
        }
    }

}
