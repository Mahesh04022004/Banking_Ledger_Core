package com.bank.banking_core.event.producer;

import com.bank.banking_core.event.model.TransactionEvent;

public interface EventPublisher {

    void publish(TransactionEvent event);

}
