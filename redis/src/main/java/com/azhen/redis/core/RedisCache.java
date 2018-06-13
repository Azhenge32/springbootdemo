package com.azhen.redis.core;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;

public class RedisCache {

	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

	public final int EXPIRE = 7200;

	public final String CHANNEL_CONSOLE = "console";

	protected JedisPool jedisPool;

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	protected Jedis getConnection() throws JedisException {
		Jedis jedis = null;
		try {
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

	public <T> void set(String key, T value) {
		set(key, value, EXPIRE);
	}

	public <T> void set(String key, T value, int cacheSeconds) {
		Jedis jedis = null;
		try {
			jedis = this.getConnection();
			jedis.set(key, JSON.toJSONString(value));
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (JedisException e) {
			logger.warn("set:", key, e);
		} finally {
			releaseConnection(jedis);
		}
	}

	public <T> void set(Map<String, T> batchData, int cacheSeconds) {
		Jedis jedis = null;

		try {
			jedis = this.getConnection();

			Pipeline pipeline = jedis.pipelined();
			for (Map.Entry<String, T> element : batchData.entrySet()) {
				if (cacheSeconds != 0) {
					pipeline.setex(element.getKey(), cacheSeconds,
							JSON.toJSONString(element.getValue()));
				} else {
					pipeline.set(element.getKey(),
							JSON.toJSONString(element.getValue()));
				}
			}
			pipeline.sync();
		} catch (JedisException e) {
			logger.error(e.getMessage(),e);
		} finally {
			releaseConnection(jedis);
		}
	}

	public <T> T get(String key, Class<T> clazz) {

		Jedis jedis = null;
		try {
			jedis = this.getConnection();
			if (jedis.exists(key)) {
				String value = jedis.get(key);
				value = StringUtils.isNotBlank(value)
						&& !"nil".equalsIgnoreCase(value) ? value : null;
				if (value != null)
					return JSON.parseObject(value, clazz);
			}
		} catch (JedisException e) {
			logger.warn("get:", key, e);
		} finally {
			releaseConnection(jedis);
		}
		return null;
	}

	public <T> List<T> get(String[] keys, Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis = this.getConnection();

			List<String> values = jedis.mget(keys);
			List<T> results = new ArrayList<T>(values.size());
			for (String value : values) {
				results.add(JSON.parseObject(value, clazz));
			}
			return results;
		} catch (JedisException e) {
			logger.warn("get:", Arrays.toString(keys), e);
		} finally {
			releaseConnection(jedis);
		}
		return null;
	}

	public <T> void addList(String key, List<T> list) {
		addList(key, list, EXPIRE);
	}

	public <T> void addList(String key, List<T> list, int cacheSeconds) {
		if (list != null && list.size() >= 0) {
			Jedis jedis = null;

			try {
				jedis = this.getConnection();
				if (jedis.exists(key)) {
					jedis.del(key);
				}
				for (T aList : list) {
					jedis.rpush(key, JSON.toJSONString(aList));
				}
				if (cacheSeconds != 0) {
					jedis.expire(key, cacheSeconds);
				}
			} catch (JedisException e) {
				logger.warn("addList:", key, list.size(), e);
			} finally {
				releaseConnection(jedis);
			}
		}
	}

	public <T> List<T> getList(String key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis = this.getConnection();
			if (jedis.exists(key)) {
				List<String> values = jedis.lrange(key, 0, -1);
				List<T> results = new ArrayList<T>(values.size());
				for (String value : values) {
					results.add(JSON.parseObject(value, clazz));
				}
				return results;
			}
		} catch (JedisException e) {
			logger.warn("getList:", key, e);
		} finally {
			releaseConnection(jedis);
		}
		return null;
	}

	public <T> void appendSet(String key, T[] values) {
		Jedis jedis = null;
		try {
			jedis = this.getConnection();
			Pipeline pipeline = jedis.pipelined();
			for (T value : values) {
				pipeline.rpush(key, JSON.toJSONString(value));
			}
			pipeline.sync();
		} catch (JedisException e) {
			logger.warn("appendSet:", key, e);
		} finally {
			releaseConnection(jedis);
		}
	}

	public <T> void removeSet(String key, T value) {
		Jedis jedis = null;
		try {
			jedis = this.getConnection();
			jedis.srem(key, JSON.toJSONString(value));

		} catch (JedisException e) {
			logger.warn("removeSet:", key, value, e);
		} finally {
			releaseConnection(jedis);
		}
	}

	public <T> void push(String key, T value, int cacheSeconds) {
		Jedis jedis = null;
		try {
			jedis = this.getConnection();

			jedis.rpush(key, JSON.toJSONString(value));
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			logger.warn("push:", key, e);
		} finally {
			releaseConnection(jedis);
		}
	}

	public <T> void push(String key, List<T> datas, int cacheSeconds) {
		Jedis jedis = null;

		try {
			jedis = this.getConnection();
			jedis.del(key);

			for (T data : datas) {
				jedis.rpush(key, JSON.toJSONString(data));
			}
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}

		} catch (JedisException e) {
			logger.warn("push:", datas != null ? datas.size() : 0, e);
		} finally {
			releaseConnection(jedis);
		}
	}

	/**
	 * 删除list中的元素
	 * 
	 * @param key
	 * @param values
	 * @param methodName
	 */
	public <T> void pop(String key, List<T> values) {
		Jedis jedis = null;

		try {
			jedis = this.getConnection();

			Pipeline pipeline = jedis.pipelined();
			if (values != null && !values.isEmpty()) {
				for (T val : values) {
					pipeline.lrem(key, 0, JSON.toJSONString(val));
				}
			}
			pipeline.sync();
		} catch (JedisException e) {

			logger.warn("pop:", values != null ? values.size() : 0, e);
		} finally {
			releaseConnection(jedis);
		}
	}

	public <T> void setMap(String key, Map<String, T> datas, int cacheSeconds) {

		Jedis jedis = null;
		if (datas != null && datas.size() > 0) {
			try {
				jedis = this.getConnection();
				Map<String, String> values = new HashMap<>(
						datas.size());
				for (Map.Entry<String, T> entry : datas.entrySet()) {
					values.put(entry.getKey(),
							JSON.toJSONString(entry.getValue()));
				}
				jedis.hmset(key, values);
				if (cacheSeconds >= 0)
					jedis.expire(key, cacheSeconds);
			} catch (JedisException e) {
				logger.warn("setMap:", key, datas.size(), e);
			} finally {
				releaseConnection(jedis);
			}
		}
	}

	public <T> void setMap(String key, String field, T value, int cacheSeconds) {

		Jedis jedis = null;
		try {
			jedis = this.getConnection();
			if (jedis != null) {
				jedis.hset(key, field, JSON.toJSONString(value));
				jedis.expire(key, cacheSeconds);
			}
		} catch (JedisException e) {
			logger.warn("setMap:", key, field, e);
		} finally {
			releaseConnection(jedis);
		}
	}

	public <T> Set<T> getSet(String key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis = this.getConnection();
			if (jedis.exists(key)) {
				Set<String> values = jedis.smembers(key);
				Set<T> results = new HashSet<T>(values.size());
				for (String value : values) {
					results.add(JSON.parseObject(value, clazz));
				}
				return results;
			}
		} catch (JedisException e) {
			logger.warn("getSet:", key, e);
		} finally {
			releaseConnection(jedis);
		}
		return null;
	}

	public <T> Map<String, T> getMap(String key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis = this.getConnection();
			Map<String, String> values = jedis.hgetAll(key);
			Map<String, T> results = null;
			if (values.size() > 0) {
				results = new HashMap<String, T>(values.size());
				for (Map.Entry<String, String> entry : values.entrySet()) {
					results.put(entry.getKey(),
							JSON.parseObject(entry.getValue(), clazz));
				}
			}
			return results != null ? results : new HashMap<String, T>();

		} catch (JedisException e) {
			logger.warn("getMap:", key, e);
		} finally {
			releaseConnection(jedis);
		}
		return null;
	}

	protected <T> T getMapValue(String key, String field, Class<T> clazz) {

		Jedis jedis = null;
		try {
			jedis = this.getConnection();
			if (jedis.exists(key)) {
				String value = jedis.hget(key, field);
				return JSON.parseObject(value, clazz);
			}
		} catch (JedisException e) {
			logger.warn("failed:", key, field, e);
		} finally {
			releaseConnection(jedis);
		}
		return null;
	}

	public Long getIdentify(String seqno) {

		Jedis jedis = null;
		Long identify = null;
		try {
			jedis = this.getConnection();
			identify = jedis.incr(seqno);
			if (identify == 0) {
				return jedis.incr(seqno);
			} else {
				return identify;
			}

		} catch (JedisException e) {

			logger.warn("getIdentify:", seqno, identify, e);
		} finally {
			releaseConnection(jedis);
		}
		return null;
	}

	/**
	 * 删除某db的某个key值
	 * 
	 * @param key
	 * @return
	 */
	public Long delete(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getConnection();
			return jedis.del(key);
		} catch (JedisException e) {
			logger.warn("delete:", key, e);
		} finally {
			releaseConnection(jedis);
		}
		return 0l;
	}

	public boolean exist(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getConnection();
			return jedis.exists(key);
		} catch (JedisException e) {
			logger.warn("exist:", key, e);
		} finally {
			releaseConnection(jedis);
		}
		return false;
	}

	public long publisher(Map<String, String> message) {
		Jedis jedis = null;
		try {
			jedis = this.getConnection();
			return jedis.publish(CHANNEL_CONSOLE, JSON.toJSONString(message));
		} catch (JedisException e) {
			logger.error(e.getLocalizedMessage(),e);
		} finally {
			releaseConnection(jedis);
		}
		return -1l;
	}

	public void subscriber(JedisPubSub jedisPubSub) {
		Jedis jedis = null;
		try {
			jedis = this.getConnection();
			jedis.subscribe(jedisPubSub, CHANNEL_CONSOLE);
		} catch (JedisException e) {
			logger.error(e.getLocalizedMessage(),e);
		} finally {
			releaseConnection(jedis);
		}
	}
}
