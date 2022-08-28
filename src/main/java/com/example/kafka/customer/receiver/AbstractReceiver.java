package com.example.kafka.customer.receiver;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractReceiver implements EventReceiver {
    private final Set<EventListener> listeners = new HashSet<>();

    @Override
    public final void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public final void fire(ReceiveEvent event) {
        for (var listener : listeners) {
            listener.onEvent(event);
        }
    }
}
