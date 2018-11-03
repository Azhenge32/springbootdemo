package com.azhen.redis.core;

import com.azhen.redis.core.lock.RedisOperate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

public class BaseRedisService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private JedisPool jedisPool;

    public BaseRedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    protected <T> T doOperate(RedisOperate<T> redisOperate) {
	    Jedis jedis = getConnection();
	    T out = redisOperate.operate(jedis);
	    releaseConnection(jedis);
        return out;
	}

	protected Jedis getConnection() throws JedisException {
		Jedis jedis = null;
		try {
		    if (jedisPool == null) {
		        jedisPool = new JedisPool();
            }
			jedis = jedisPool.getResource();
		} catch (JedisException e) {
			logger.warn("jedisPool getResource.", e);
			if (jedis != null) {
				jedis.close();
			}
			throw e;
		}
		return jedis;
	}

	public void releaseConnection(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
}
