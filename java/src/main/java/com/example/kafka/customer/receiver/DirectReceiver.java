package com.example.kafka.customer.receiver;

import com.example.kafka.customer.CustomerPayloadDeserializer;
import com.obsidiandynamics.worker.WorkerOptions;
import com.obsidiandynamics.worker.WorkerThread;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class DirectReceiver extends AbstractReceiver {

    private final WorkerThread pollingThread;
    private final Consumer<String, CustomerPayloadOrError> consumer;
    private final Duration pollTimeout;

    public DirectReceiver(Map<String, Object> consumerConfig, String topic, Duration pollTimeout) {
        this.pollTimeout = pollTimeout;

        final var mergedConfig = new HashMap<String, Object>();
        mergedConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        mergedConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomerPayloadDeserializer.class.getName());
        mergedConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        mergedConfig.putAll(consumerConfig);

        consumer = new KafkaConsumer<>(mergedConfig);
        consumer.subscribe(Set.of(topic));

        pollingThread = WorkerThread.builder()
            .withOptions(new WorkerOptions()
                .daemon()
                .withName(DirectReceiver.class, "poller")
            )
            .onCycle(this::onPollCycle)
            .build();
    }

    private void onPollCycle(WorkerThread thread) throws InterruptedException {
        final ConsumerRecords<String, CustomerPayloadOrError> records;

        try {
            records = consumer.poll(pollTimeout);
        } catch (InterruptException e) {
            throw new InterruptedException("Interrupted during poll");
        }

        if (records.isEmpty()) {
            return;
        }

        for (var record : records) {
            final var payloadOrError = record.value();
            final var event = ReceiveEvent.from(payloadOrError, record);
            fire(event);
        }
        consumer.commitAsync();
    }

    @Override
    public void start() {
        pollingThread.start();
    }

    @Override
    public void close() {
        pollingThread.terminate().joinSilently();
        consumer.close();
    }
}
