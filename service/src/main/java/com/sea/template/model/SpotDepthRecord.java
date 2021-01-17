package com.sea.template.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sea.template.lang.RawRecordStatus;
import org.joda.time.DateTime;

import java.util.List;

public class SpotDepthRecord {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("instrument")
    private final String instrument;

    @JsonProperty("asks")
    private final List<BidAsk> asks;

    @JsonProperty("bids")
    private final List<BidAsk> bids;

    @JsonProperty("timestamp")
    private final DateTime timestamp;

    @JsonProperty("status")
    private final RawRecordStatus status;

    public String getId() {
        return id;
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

    public RawRecordStatus getStatus() {
        return status;
    }

    public String getInstrument() {
        return instrument;
    }

    private SpotDepthRecord(String id, String instrument, List<BidAsk> asks, List<BidAsk> bids, DateTime timestamp, RawRecordStatus status) {
        this.id = id;
        this.instrument = instrument;
        this.asks = asks;
        this.bids = bids;
        this.timestamp = timestamp;
        this.status = status;

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String instrument;
        private List<BidAsk> asks;
        private List<BidAsk> bids;
        private DateTime timestamp;
        private RawRecordStatus status;

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder instrument(String instrument){
            this.instrument = instrument;
            return this;
        }

        public Builder asks(List<BidAsk> asks){
            this.asks = asks;
            return this;
        }

        public Builder bids(List<BidAsk> bids){
            this.bids = bids;
            return this;
        }

        public Builder timestamp(DateTime timestamp){
            this.timestamp = timestamp;
            return this;
        }

        public Builder status(String status) {
            this.status = RawRecordStatus.valueOf(status);
            return this;
        }

        public SpotDepthRecord build() {
            return new SpotDepthRecord(this.id, this.instrument, this.asks, this.bids, this.timestamp, this.status);
        }
    }

}
