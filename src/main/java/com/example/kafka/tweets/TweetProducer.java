package com.example.kafka.tweets;

import com.example.kafka.tweets.dto.Tweet;
import com.example.kafka.tweets.dto.TweetSerializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.*;
import java.util.List;
import java.util.Map;

public final class TweetProducer {
    public static void main(String[] args) throws IOException, InterruptedException {
        final String topic = "tweets";

        final Map<String, Object> config = Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName(),
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TweetSerializer.class.getName(),
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true
        );

        List<Tweet> tweets = readTweetsFromFile("src/main/resources/tweets.json");

        try (var producer = new KafkaProducer<String, Tweet>(config)) {
            for (var tweet : tweets) {
                final String key = "myKey";
                System.out.format("Publishing record with value: %s\n", tweet);

                final Callback callback = (metadata, exception) -> {
                    System.out.format("Published with metadata: %s, error: %s\n", metadata, exception);
                };

                producer.send(new ProducerRecord<>(topic, key, tweet), callback);
                Thread.sleep(5_000);
            }
        }
    }

    private static List<Tweet> readTweetsFromFile(String filename) throws IOException {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);

        return mapper.readValue(new FileInputStream(filename), new TypeReference<>() {
        });
    }
}
