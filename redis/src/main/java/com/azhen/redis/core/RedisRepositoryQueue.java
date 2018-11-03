package com.azhen.redis.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class RedisRepositoryQueue<T> {
    private Consumer<List<T>> repository;
    private Jedis jedis;
    private String queueKey;

    // fastjson 序列化对象中存在 generic 类型时，需要使用 TypeReference
    private Type Type = new TypeReference<T>(){}.getType();

    public RedisRepositoryQueue(Jedis jedis, String queueKey, Consumer<List<T>> repository) {
        this.jedis = jedis;
        this.queueKey = queueKey;
        this.repository = repository;
    }

    public void submit(List<T> list) {
        for (T t : list) {
            String s = JSON.toJSONString(t);
            jedis.zadd(queueKey, System.currentTimeMillis(), s);
        }
    }

    public void loop() {
        while (!Thread.interrupted()) {
            // 读取50条
            Set<String> values = jedis.zrangeByScore(queueKey, 0, System.currentTimeMillis(), 0, 50);
            if (values.isEmpty()) {
                try {
                    Thread.sleep(500); // 歇会继续
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }
            String s = values.iterator().next();
            String[] members = new String[values.size()];
            members = values.toArray(members);
            if (jedis.zrem(queueKey, members) > 0) { // 抢到了
                List<T> list = JSON.parseObject(s, Type); // fastjson 反序列化
                repository.accept(list);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(TimeUnit.MINUTES.toDays(1));
    }
}