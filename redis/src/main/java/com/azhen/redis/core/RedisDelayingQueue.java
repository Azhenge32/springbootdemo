package com.azhen.redis.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;

public class RedisDelayingQueue<T> extends BaseRedisService{

    static class TaskItem<T> {
        public String id;
        public T msg;
    }

    // fastjson 序列化对象中存在 generic 类型时，需要使用 TypeReference
    private Type TaskType = new TypeReference<TaskItem<T>>() {
    }.getType();

    private String queueKey;

    public RedisDelayingQueue(JedisPool jedisPool, String queueKey) {
        super(jedisPool);
        this.queueKey = queueKey;
    }

    public void delay(T msg) {
        TaskItem<T> task = new TaskItem<T>();
        task.id = UUID.randomUUID().toString(); // 分配唯一的 uuid
        task.msg = msg;
        String s = JSON.toJSONString(task); // fastjson 序列化
        // 塞入延时队列 ,5s 后此消息才会被取出
        doOperate(jedis -> jedis.zadd(queueKey, System.currentTimeMillis() + 5000, s));

    }

    public void loop() {
        while (!Thread.interrupted()) {
            // 只取一条
            Set<String> values = doOperate(jedis -> jedis.zrangeByScore(queueKey, 0, System.currentTimeMillis(), 0, 1));
            if (values.isEmpty()) {
                try {
                    Thread.sleep(500); // 歇会继续
                } catch (InterruptedException e) {
                    break;
                }
                continue;
            }
            String s = values.iterator().next();
            if (doOperate(jedis -> jedis.zrem(queueKey, s)) > 0) { // 抢到了
                TaskItem<T> task = JSON.parseObject(s, TaskType); // fastjson 反序列化
                this.handleMsg(task.msg);
            }
        }
    }

    public void handleMsg(T msg) {
        try {
            System.out.println(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       Jedis jedis = new Jedis();
       JedisPool jedisPool = new JedisPool();
        RedisDelayingQueue<String> queue = new RedisDelayingQueue<String>(jedisPool,"q-demo");
        Thread producer = new Thread() {

            public void run() {
                for (int i = 0; i < 10; i++) {
                    queue.delay("codehole" + i);
                }
            }

        };
        Thread consumer = new Thread() {

            public void run() {
                queue.loop();
            }

        };
        producer.start();
        consumer.start();
        try {
            producer.join();
            Thread.sleep(60000);
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {
        }
    }
}