package com.sea.template.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;

public class BidAsk {
    @JsonProperty("price")
    private final BigDecimal price;

    @JsonProperty("size")
    private final BigDecimal size;

    @JsonProperty("numOfOrders")
    private final Integer numOfOrders;

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getSize() {
        return size;
    }

    public Integer getNumOfOrders() {
        return numOfOrders;
    }

    public BidAsk(BigDecimal price, BigDecimal size, Integer numOfOrders) {
        this.price = price;
        this.size = size;
        this.numOfOrders = numOfOrders;
    }

    public static BidAsk.Builder builder() {
        return new BidAsk.Builder();
    }

    public static class Builder {
        private BigDecimal price;
        private BigDecimal size;
        private Integer numOfOrders;

        public BidAsk.Builder price(String price){
            this.price = new BigDecimal(price);
            return this;
        }

        public BidAsk.Builder size(String size){
            this.size = new BigDecimal(size);
            return this;
        }

        public BidAsk.Builder numOfOrders(String numOfOrders){
            this.numOfOrders = Integer.valueOf(numOfOrders);
            return this;
        }

        public BidAsk build() {
            return new BidAsk(this.price, this.size, this.numOfOrders);
        }
    }
}
