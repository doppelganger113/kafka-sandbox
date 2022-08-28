package com.example.kafka.tweets.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class TweetDeserializer implements Deserializer<Tweet> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Tweet deserialize(String topic, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, Tweet.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
