package com.azhen.redis;

import com.azhen.redis.core.RedisCache;
import com.azhen.redis.core.lock.RedisReentrantLock;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(4000);
        jedisPoolConfig.setMinIdle(5);
        jedisPoolConfig.setMaxTotal(200);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 5000);
        return jedisPool;
    }

    @Bean
    public RedisCache redisCache(JedisPool jedisPool) {
        return new RedisCache(jedisPool);
    }

    @Bean
    public RedisReentrantLock redisWithReentrantLock(JedisPool jedisPool) {
        return new RedisReentrantLock(jedisPool);
    }
}
