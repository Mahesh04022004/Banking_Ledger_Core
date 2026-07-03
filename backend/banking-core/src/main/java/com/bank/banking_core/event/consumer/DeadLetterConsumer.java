package com.bank.banking_core.event.consumer;

import com.bank.banking_core.config.KafkaTopicConfig;
import com.bank.banking_core.event.model.TransactionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class DeadLetterConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(DeadLetterConsumer.class);

    @KafkaListener(
            topics = KafkaTopicConfig.TRANSACTION_TOPIC + "-dlt",
            groupId = "dlt-group"
    )
    public void consume(TransactionEvent event) {

        logger.error(
                "Received message in DLT: {}",
                event.getTransactionReference()
        );
    }
}
