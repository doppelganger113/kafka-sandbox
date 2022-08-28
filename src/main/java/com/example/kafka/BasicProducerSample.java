package com.example.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;

public final class BasicProducerSample {
    public static void main(String[] args) throws InterruptedException {
        final String topic = "getting-started";

        final Map<String, Object> config = Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName(),
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName(),
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true
        );

//        final var config = new TypesafeProducerConfig()
//            .withBootstrapServers(topic)
//            .withKeySerializerClass(StringSerializer.class)
//            .withValueSerializerClass(StringSerializer.class)
//            .withCustomEntry(
//                ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1
//            )
//            .mapify();

        try (var producer = new KafkaProducer<String, String>(config)) {
            var counter = 0L;
            while (true) {
                final String key = "myKey";
                final String value = "Counter value: " + counter;
                System.out.format("Publishing record with value: %s\n", value);

                final Callback callback = (metadata, exception) -> {
                    System.out.format("Published with metadata: %s, error: %s\n", metadata, exception);
                };

                producer.send(new ProducerRecord<>(topic, key, value), callback);
                counter++;
                Thread.sleep(5_000);
            }
        }
    }
}
