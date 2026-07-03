package com.bank.banking_core.service;

import java.util.List;

public interface DistributedLockService {

    void executeWithLocks(
            List<String> lockKeys,
            Runnable action
    );

}
