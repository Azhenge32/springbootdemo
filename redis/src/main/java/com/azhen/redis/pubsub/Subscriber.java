package com.azhen.redis.pubsub;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;

public class Subscriber extends JedisPubSub {

	private static final Logger log = LoggerFactory.getLogger(Subscriber.class);

	@Override
	public void onMessage(String channel, String message) {

		@SuppressWarnings("unchecked")
		Map<String, String> param = JSON.parseObject(message, Map.class);

		String type = param.get("type");
		System.out.println(channel + ":" + type);
	}
}
