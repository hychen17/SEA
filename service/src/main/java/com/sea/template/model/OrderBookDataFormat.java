package com.sea.template.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "poller")
public class OrderBookDataFormat {
    private List<SpotDepth> spotDepths;

    public List<SpotDepth> getSpotDepths() {
        return spotDepths;
    }

    public void setSpotDepths(List<SpotDepth> spotDepths) {
        this.spotDepths = spotDepths;
    }
}
