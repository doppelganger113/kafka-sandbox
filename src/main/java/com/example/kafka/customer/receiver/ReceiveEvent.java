package com.example.kafka.customer.receiver;

import com.example.kafka.customer.CustomerPayload;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ReceiveEvent {
    private final CustomerPayload payload;
    private final Throwable err;
    private final ConsumerRecord<String, ?> record;

    public static ReceiveEvent from(CustomerPayloadOrError payloadOrError, ConsumerRecord<String, ?> record) {
        return new ReceiveEvent(
            payloadOrError.getCustomerPayload(),
            payloadOrError.getError(),
            record,
            payloadOrError.getEncodedValue()
        );
    }

    public ReceiveEvent(CustomerPayload payload, Throwable err, ConsumerRecord<String, ?> record, String encodedValue) {
        this.payload = payload;
        this.err = err;
        this.record = record;
        this.encodedValue = encodedValue;
    }

    public boolean isError() {
        return err != null;
    }

    public CustomerPayload getPayload() {
        return payload;
    }

    public Throwable getErr() {
        return err;
    }

    public ConsumerRecord<String, ?> getRecord() {
        return record;
    }

    public String getEncodedValue() {
        return encodedValue;
    }

    private final String encodedValue;

    @Override
    public String toString() {
        return "ReceiveEvent{" +
            "payload=" + payload +
            ", err=" + err +
            ", record=" + record +
            ", encodedValue='" + encodedValue + '\'' +
            '}';
    }
}
