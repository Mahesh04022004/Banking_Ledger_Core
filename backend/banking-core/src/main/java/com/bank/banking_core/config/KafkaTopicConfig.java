package com.bank.banking_core.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    public static final String TRANSACTION_TOPIC = "transaction-events";
    public static final String TRANSACTION_DLT = "transaction-events-dlt";

    @Bean
    public NewTopic transactionTopic() {
        return new NewTopic(TRANSACTION_TOPIC, 3, (short) 1);
    }

    @Bean
    public NewTopic transactionDeadLetterTopic() {
        return new NewTopic(TRANSACTION_DLT, 3, (short) 1);
    }
}
