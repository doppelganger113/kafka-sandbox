package com.example.kafka.tweets.dto;

import com.example.kafka.tweets.TweetUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Tweet(
    @JsonProperty("CreatedAt")
    Long createdAt,

    @JsonProperty("Id")
    Integer id,

    @JsonProperty("Text")
    String text,

    @JsonProperty("Lang")
    String lang,

    @JsonProperty("Retweet")
    Boolean retweet,

    @JsonProperty("Source")
    String source,

    @JsonProperty("User")
    TweetUser user
    ) {
}
