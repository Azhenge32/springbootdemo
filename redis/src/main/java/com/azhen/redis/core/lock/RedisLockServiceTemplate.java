package com.azhen.redis.core.lock;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;

@Service
public class RedisLockServiceTemplate {
    @Resource
    RedisReentrantLock lock;
    public <T> T service(String key, RedisLockService<T> service) {
        String uuid = UUID.randomUUID().toString();
        try {
            Random random = new Random();
            while (!lock.lock(key, uuid)) {
                try {
                    Thread.sleep(random.nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 业务代码
            return service.service();
        } finally {
            lock.unlock(key, uuid);
        }
    }
}
