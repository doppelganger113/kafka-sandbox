package com.example.kafka.customer;

import com.example.kafka.customer.receiver.CustomerPayloadOrError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class CustomerPayloadDeserializer implements Deserializer<CustomerPayloadOrError> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public CustomerPayloadOrError deserialize(String topic, byte[] bytes) {
        try {
            var payload = objectMapper.readValue(bytes, CustomerPayload.class);
            return new CustomerPayloadOrError(payload, null, new String(bytes));
        } catch (IOException e) {
            return new CustomerPayloadOrError(null, e, new String(bytes));
        }
    }
}
