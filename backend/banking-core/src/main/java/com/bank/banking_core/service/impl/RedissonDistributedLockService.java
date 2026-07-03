package com.bank.banking_core.service.impl;

import com.bank.banking_core.constants.ApiMessages;
import com.bank.banking_core.exception.LockAcquisitionException;
import com.bank.banking_core.service.DistributedLockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
public class RedissonDistributedLockService
        implements DistributedLockService {

    private final RedissonClient redissonClient;

    public RedissonDistributedLockService(
            RedissonClient redissonClient) {

        this.redissonClient = redissonClient;
    }

    @Override
    public <T> T executeWithLocks(
            List<String> lockKeys,
            Supplier<T> action) {

        List<RLock> locks = new ArrayList<>();

        try {

            for (String key : lockKeys) {

                RLock lock = redissonClient.getLock(key);

                boolean acquired = lock.tryLock(
                        5,
                        10,
                        TimeUnit.SECONDS
                );

                if (!acquired) {
                    throw new LockAcquisitionException(
                            ApiMessages.LOCK_ACQUISITION_FAILED
                    );
                }

                locks.add(lock);
            }

            return action.get();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new LockAcquisitionException(
                    ApiMessages.LOCK_ACQUISITION_FAILED
            );

        } finally {

            for (int i = locks.size() - 1; i >= 0; i--) {

                RLock lock = locks.get(i);

                if (lock != null && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }
}
