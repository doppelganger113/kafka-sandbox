package com.example.kafka.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SuspendCustomer extends CustomerPayload {

    public static final String TYPE = "SUSPEND_CUSTOMER";

    public SuspendCustomer(
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
        return "SuspendCustomer{id='" + this.getId().toString() + "'}";
    }
}
