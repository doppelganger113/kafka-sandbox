package com.example.kafka.tweets;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TweetUser(
    @JsonProperty("Id")
    String id,

    @JsonProperty("Name")
    String name,

    @JsonProperty("Description")
    String description,

    @JsonProperty("ScreenName")
    String screenName,

    @JsonProperty("URL")
    String url,

    @JsonProperty("FollowersCount")
    Long followersCount,

    @JsonProperty("FriendsCount")
    Long friendsCount
) {
}
