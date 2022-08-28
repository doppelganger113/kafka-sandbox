package com.example.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class KafkaStreamsApp {
    public static void main(String[] args) {
        final String inTopic = "getting-started";
        final String outTopic = "getting-started-outer";

        var props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-kafka-streams-app-id");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");

        var stringSerde = Serdes.String();
        var builder = new StreamsBuilder();

        KStream<String, String> simpleFirstStream = builder.stream(
            inTopic, Consumed.with(stringSerde, stringSerde)
        );

        KStream<String, String> upperCasedStream = simpleFirstStream
            .peek((k, v) -> System.out.println("consumed: key " + k + " value: " + v))
            .mapValues(v -> v.toUpperCase());
        upperCasedStream.to(outTopic, Produced.with(stringSerde, stringSerde));

        try (var kafkaStreams = new KafkaStreams(builder.build(), props)) {
            System.out.println("Starting KafkaStreamsApp...");
            final var countDownLatch = new CountDownLatch(1);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutting down the KafkaStreamsApp...");
                kafkaStreams.close();
                countDownLatch.countDown();
            }));

            try {
                kafkaStreams.start();
                countDownLatch.await();
            } catch (Throwable e) {
                System.out.println("Error shutting down: " + e.getMessage());
                System.exit(1);
            }
            System.exit(0);
        }
    }
}
