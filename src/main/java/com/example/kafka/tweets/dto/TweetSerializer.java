package com.example.kafka.tweets.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Serial;

public class TweetSerializer implements Serializer<Tweet> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public static class MarshallingException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 1;

        private MarshallingException(Throwable throwable) {
            super(throwable);
        }
    }

    @Override
    public byte[] serialize(String topic, Tweet tweet) {
        try {
            return objectMapper.writeValueAsBytes(tweet);
        } catch (JsonProcessingException e) {
            throw new MarshallingException(e);
        }
    }
}
