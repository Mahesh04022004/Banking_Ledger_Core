package com.bank.banking_core.event.producer;

import com.bank.banking_core.config.KafkaTopicConfig;
import com.bank.banking_core.event.model.TransactionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaEventPublisher implements EventPublisher {

    private static final Logger logger =
            LoggerFactory.getLogger(KafkaEventPublisher.class);

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(TransactionEvent event) {

        CompletableFuture<SendResult<String, TransactionEvent>> future =
                kafkaTemplate.send(
                        KafkaTopicConfig.TRANSACTION_TOPIC,
                        event.getTransactionReference(),
                        event
                );

        future.whenComplete((result, ex) -> {

            if (ex != null) {

                logger.error(
                        "Failed to publish transaction event: {}",
                        event.getTransactionReference(),
                        ex
                );

            } else {

                logger.info(
                        "Transaction event [{}] published successfully. Topic: {}, Partition: {}, Offset: {}",
                        event.getTransactionReference(),
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()
                );
            }
        });
    }
}
