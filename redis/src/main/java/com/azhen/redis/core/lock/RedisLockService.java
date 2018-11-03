package com.azhen.redis.core.lock;

import redis.clients.jedis.Jedis;

public interface RedisLockService<T> {
    T service();
}
