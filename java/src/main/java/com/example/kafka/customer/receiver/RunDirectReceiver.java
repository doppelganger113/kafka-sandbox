package com.example.kafka.customer.receiver;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.time.Duration;
import java.util.Map;

public final class RunDirectReceiver {
    public static void main(String[] args) throws InterruptedException {
        final Map<String, Object> consumerConfig = Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
            ConsumerConfig.GROUP_ID_CONFIG, "customer-direct-consumer",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
        );

        System.out.println("Starting direct receiver...");
        try (var receiver = new DirectReceiver(
            consumerConfig, "customer.test", Duration.ofMillis(100))
        ) {
            new ConsumerBusinessLogic(receiver);
            receiver.start();
            Thread.sleep(10_000);
        }
        System.out.println("Done.");
    }
}
