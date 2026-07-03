package com.bank.banking_core.event.consumer;

import com.bank.banking_core.config.KafkaTopicConfig;
import com.bank.banking_core.event.model.TransactionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Component
public class AuditConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(AuditConsumer.class);

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(
                    delay = 1000,
                    multiplier = 2.0
            ),
            dltTopicSuffix = "-dlt"
    )
    @KafkaListener(
            topics = KafkaTopicConfig.TRANSACTION_TOPIC,
            groupId = "audit-group"
    )
    public void consume(TransactionEvent event) {

        logger.info("""
                
                ===============================
                TRANSACTION AUDIT EVENT
                ===============================
                Reference : {}
                Type      : {}
                From      : {}
                To        : {}
                Amount    : {}
                Time      : {}
                ===============================
                """,
                event.getTransactionReference(),
                event.getEventType(),
                event.getFromAccount(),
                event.getToAccount(),
                event.getAmount(),
                event.getTimestamp()
        );
    }
}
