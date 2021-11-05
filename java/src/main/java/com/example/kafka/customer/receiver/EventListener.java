package com.example.kafka.customer.receiver;

@FunctionalInterface
public interface EventListener {
    void onEvent(ReceiveEvent event);
}
