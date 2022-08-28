package com.example.kafka.tweets;

import com.example.kafka.tweets.dto.Tweet;
import com.example.kafka.tweets.dto.TweetSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.util.Map;

public class TweetStreamTopology {
    public static Topology build(String topic) {
        var builder = new StreamsBuilder();
        KStream<byte[], Tweet> stream = builder.stream(topic, Consumed.with(Serdes.ByteArray(), new TweetSerdes()));
        stream.print(Printed.<byte[], Tweet>toSysOut().withLabel("tweet"));

        var filteredStream = stream.filterNot((key, tweet) -> tweet.retweet());
        filteredStream.print(Printed.<byte[], Tweet>toSysOut().withLabel("not retweet"));

        Map<String, KStream<byte[], Tweet>> branches = filteredStream.split(Named.as("lang-"))
            .branch(englishTweet(), Branched.as("en"))
            .branch(nonEnglishTweet(), Branched.as("non-en"))
            .defaultBranch();

        KStream<byte[], Tweet> englishStream = branches.get("lang-en");
        KStream<byte[], Tweet> nonEnglishStream = branches.get("lang-non-en");

        englishStream.mapValues((readOnlyKey, value) -> "[ORIGINAL]: " + value.text());
        nonEnglishStream.mapValues((readOnlyKey, value) -> "[Translated]: " + value.text());

        englishStream.merge(nonEnglishStream)
            .to("");

        return builder.build();
    }

    private static Predicate<byte[], Tweet> englishTweet() {
        return (bytes, tweet) -> tweet.lang().equals("en");
    }

    private static Predicate<byte[], Tweet> nonEnglishTweet() {
        return (bytes, tweet) -> !tweet.lang().equals("en");
    }
}
