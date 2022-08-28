package com.example.kafka.customer.receiver;

public final class ConsumerBusinessLogic {
    private final EventReceiver receiver;

    public ConsumerBusinessLogic(EventReceiver receiver) {
        this.receiver = receiver;
        receiver.addListener(this::onEvent);
    }

    private void onEvent(ReceiveEvent event) {
        if (event.isError()) {
            System.err.printf("Error in record %s: %s\n", event.getRecord(), event.getErr());
            return;
        }

        System.out.printf("Received %s\n", event.getPayload());
    }
}
