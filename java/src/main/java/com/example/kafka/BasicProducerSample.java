package com.example.kafka;

import java.util.Date;
import java.util.Map;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;

public final class BasicProducerSample {
    public static void main(String[] args) throws InterruptedException {
        final String topic = "getting-started";

        final Map<String, Object> config = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName(),
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName(),
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true
        );

        try (var producer = new KafkaProducer<String, String>(config)) {
            while (true) {
                final String key = "myKey";
                final String value = new Date().toString();
                System.out.format("Publishing record with value: %s\n", value);

                final Callback callback = (metadata, exception) -> {
                    System.out.format("Published with metadata: %s, error: %s\n", metadata, exception);
                };

                producer.send(new ProducerRecord<>(topic, key, value), callback);
                Thread.sleep(1_000);
            }
        }
    }
}
