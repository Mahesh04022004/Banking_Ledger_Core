package com.bank.banking_core.service;

import java.util.List;
import java.util.function.Supplier;

public interface DistributedLockService {

    <T> T executeWithLocks(
            List<String> lockKeys,
            Supplier<T> action
    );

}
