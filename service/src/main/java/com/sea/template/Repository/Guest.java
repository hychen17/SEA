package com.sea.template.Repository;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Guest {

    @JsonProperty("guest-id")
    private final String id;

    @JsonProperty("guest-name")
    private final String name;

    private Guest(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;

        public Builder id(String id){
            this.id = id;
            return this;
        }


        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Guest build() {
            return new Guest(this.id, this.name);
        }
    }

}
