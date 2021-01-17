package com.sea.template.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "poller.spotDepths")
public class SpotDepth {
    private String instrument;

    private String size;

    private String depth;

    public String getDepth() {
        return depth;
    }

    public String getInstrument() {
        return instrument;
    }

    public String getSize() {
        return size;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }
}
