package com.azhen.redis.core.lock;

import redis.clients.jedis.Jedis;

public interface RedisOperate<T> {
    T operate(Jedis jedis);
}
