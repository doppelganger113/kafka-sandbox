package com.example.kafka.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ReinstateCustomer extends CustomerPayload {

    public static final String TYPE = "REINSTATE_CUSTOMER";

    public ReinstateCustomer(
            @JsonProperty("id") UUID id
    ) {
        super(id);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return "ReinstateCustomer{id='" + this.getId().toString() + '}';
    }
}
