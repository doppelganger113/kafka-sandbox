package com.example.kafka.customer;

import com.example.kafka.customer.CustomerPayload;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.Closeable;
import java.io.Serial;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface EventSender extends Closeable {
    Future<RecordMetadata> send(CustomerPayload payload);

    final class SendException extends Exception {
        @Serial
        private static final long serialVersionUID = 1L;

        SendException(Throwable cause) {
            super(cause);
        }
    }

    default RecordMetadata blockingSend(CustomerPayload payload) throws SendException, InterruptedException {
        try {
            return send(payload).get();
        } catch (ExecutionException e) {
            throw new SendException(e);
        }
    }
}
