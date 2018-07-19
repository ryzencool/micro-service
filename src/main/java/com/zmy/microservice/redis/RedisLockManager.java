package com.zmy.microservice.redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * @description: the implementation of distribute redis lock
 * @author: zmy
 * @create: 2018/6/25
 */
public class RedisLockManager {


    private JedisPool jedisPool;

    public RedisLockManager(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final int DEFAULT_MAX_WAITING_LOCK_MILLS = 3000;
    private static final Long RELEASE_SUCCESS = 1L;


    /**
     * acquire lock
     *
     * @param key
     * @param value
     * @param expireTime
     */
    public RedisLock acquire(String key, String value, int expireTime) throws LockException {
        try (Jedis jedis = jedisPool.getResource()) {
            String result = jedis.set(key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            if (LOCK_SUCCESS.equals(result)) {
                return new RedisLock(this, key, value);
            }
            throw new LockException(key);
        }
    }

    public RedisLock acquire(String key, String value) throws LockException {
        return this.acquire(key, value, DEFAULT_MAX_WAITING_LOCK_MILLS);
    }

    /**
     * release lock
     *
     * @param lock
     * @throws LockException
     */
    public void release(RedisLock lock) throws LockException {
        try (Jedis jedis = jedisPool.getResource()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object res = jedis.eval(script, Collections.singletonList(lock.getKey()), Collections.singletonList(lock.getValue()));
            if (!RELEASE_SUCCESS.equals(res)) {
                throw new LockException(lock.getKey());
            }
        }
    }
}

