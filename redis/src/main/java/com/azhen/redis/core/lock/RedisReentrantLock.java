package com.azhen.redis.core.lock;

import com.azhen.redis.core.BaseRedisService;
import redis.clients.jedis.JedisPool;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisReentrantLock extends BaseRedisService {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final Long RELEASE_SUCCESS = 1L;
    private ThreadLocal<Map<String, Integer>> lockers = new ThreadLocal<>();

    public RedisReentrantLock(JedisPool jedisPool) {
        super(jedisPool);
    }

    private boolean _lock(String key, String requestId) {
        return doOperate((jedis) -> {
            String result = jedis.set(key, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, TimeUnit.MINUTES.toSeconds(1));
            return LOCK_SUCCESS.equals(result);
        });
    }

    private boolean _unlock(String key, String requestId) {
        return doOperate((jedis) -> {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(requestId));

            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }
            return false;
        });
    }

    private Map<String, Integer> currentLockers() {
        Map<String, Integer> refs = lockers.get();
        if (refs != null) {
            return refs;
        }
        lockers.set(new HashMap<>());
        return lockers.get();
    }

    public boolean lock(String key, String uuid) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt != null) {
            refs.put(key, refCnt + 1);
            return true;
        }
        boolean ok = this._lock(key, uuid);
        if (!ok) {
            return false;
        }
        refs.put(key, 1);
        return true;
    }

    public boolean unlock(String key, String uuid) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt == null) {
            return false;
        }
        refCnt -= 1;
        if (refCnt > 0) {
            refs.put(key, refCnt);
        } else {
            refs.remove(key);
            this._unlock(key, uuid);
        }
        return true;
    }
}
