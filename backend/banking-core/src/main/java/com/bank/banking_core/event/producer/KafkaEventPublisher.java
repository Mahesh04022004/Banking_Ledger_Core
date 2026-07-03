package com.bank.banking_core.event.producer;

import com.bank.banking_core.config.KafkaTopicConfig;
import com.bank.banking_core.event.model.TransactionEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public KafkaEventPublisher(
            KafkaTemplate<String, TransactionEvent> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(TransactionEvent event) {

        kafkaTemplate.send(
                KafkaTopicConfig.TRANSACTION_TOPIC,
                event.getTransactionReference(),
                event
        );
    }
}
