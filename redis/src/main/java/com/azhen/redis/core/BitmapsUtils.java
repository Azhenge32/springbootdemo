package com.azhen.redis.core;

import redis.clients.jedis.JedisPool;

public class BitmapsUtils extends BaseRedisService {
    public BitmapsUtils(JedisPool jedisPool) {
        super(jedisPool);
    }
}
