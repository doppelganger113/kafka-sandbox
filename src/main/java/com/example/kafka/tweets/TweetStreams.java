package com.example.kafka.tweets;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Properties;

public final class TweetStreams {
    public static void main(String[] args) {
        final String topic = "tweets";
        Topology topology = TweetStreamTopology.build(topic);

        var properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "TweetStreams");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");

        try (var streams = new KafkaStreams(topology, properties)) {
            System.out.println("Starting tweeter streams");
            streams.start();
        }
    }
}
