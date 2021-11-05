package com.example.kafka.customer.receiver;

import java.io.Closeable;

public interface EventReceiver extends Closeable {
    void addListener(EventListener listener);

    void start();

    @Override
    void close();
}
