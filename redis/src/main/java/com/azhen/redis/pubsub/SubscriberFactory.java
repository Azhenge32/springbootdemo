package com.azhen.redis.pubsub;


import com.azhen.redis.core.RedisCache;

public class SubscriberFactory {

	private RedisCache redisCache;

	private Subscriber subscriber;

	public void init() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				redisCache.subscriber(subscriber);
			}
		}).start();
	}

	public void destroy() {
		subscriber.unsubscribe();
	}

	public void setRedisCache(RedisCache redisCache) {
		this.redisCache = redisCache;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
}
