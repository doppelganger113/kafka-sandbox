package com.example.kafka.customer.receiver;

import com.example.kafka.customer.CustomerPayload;

public final class CustomerPayloadOrError {
    private final CustomerPayload customerPayload;
    private final Throwable error;
    private final String encodedValue;


    public CustomerPayloadOrError(CustomerPayload customerPayload, Throwable error, String encodedValue) {
        this.customerPayload = customerPayload;
        this.error = error;
        this.encodedValue = encodedValue;
    }

    public CustomerPayload getCustomerPayload() {
        return customerPayload;
    }

    public Throwable getError() {
        return error;
    }

    public String getEncodedValue() {
        return encodedValue;
    }

    @Override
    public String toString() {
        return "CustomerPayloadOrError{" +
                "customerPayload=" + customerPayload +
                ", error=" + error +
                ", encodedValue='" + encodedValue + '\'' +
                '}';
    }
}
