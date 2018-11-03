package com.azhen.redis.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RedisQueue<T> extends BaseRedisService{
    private Consumer<List<T>> repository;
    private Type TaskType = new TypeReference<T>() {}.getType();

    private String queueKey;

    public RedisQueue(JedisPool jedisPool, String queueKey) {
        super(jedisPool);
        this.queueKey = queueKey;
    }

    public void rpush(List<T> list) {
        String[] values = new String[list.size()];
        for (int i = 0; i < list.size(); i ++) {
            T t = list.get(i);
            values[i] = JSON.toJSONString(t);
        }
        doOperate(jedis -> jedis.rpush(queueKey, values));
    }

    public void loop() {
        while (!Thread.interrupted()) {
            List<String> strList = doOperate(jedis -> jedis.blpop(60, queueKey));
            List<T> objList = new ArrayList<>(strList.size());
            if (!CollectionUtils.isEmpty(strList)) {
                for (String str : strList) {
                    T obj = JSON.parseObject(str, TaskType);
                    objList.add(obj);
                }
                repository.accept(objList);
            }
        }
    }

    public void handleMsg(T msg) {
        try {
            System.out.println(msg);
        } catch (Exception e) {

        }
    }
}