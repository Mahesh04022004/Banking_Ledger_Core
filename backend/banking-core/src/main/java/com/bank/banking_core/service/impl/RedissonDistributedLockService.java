package com.bank.banking_core.service.impl;

import com.bank.banking_core.service.DistributedLockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedissonDistributedLockService
        implements DistributedLockService {

    private final RedissonClient redissonClient;

    public RedissonDistributedLockService(
            RedissonClient redissonClient) {

        this.redissonClient = redissonClient;
    }

    @Override
    public void executeWithLocks(
            List<String> lockKeys,
            Runnable action) {

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
                    throw new RuntimeException(
                            "Unable to acquire lock: " + key
                    );
                }

                locks.add(lock);
            }

            action.run();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException("Thread interrupted.", e);

        } finally {

            for (int i = locks.size() - 1; i >= 0; i--) {

                RLock lock = locks.get(i);

                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }
}
