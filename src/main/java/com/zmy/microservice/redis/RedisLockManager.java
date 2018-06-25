package com.zmy.microservice.redis;

/*
 * Copyright (C) 2018 The gingkoo Authors
 * This file is part of The gingkoo library.
 *
 * The gingkoo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The gingkoo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with The gingkoo.  If not, see <http://www.gnu.org/licenses/>.
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * @description: the implementation of distribute redis lock
 * @author: zmy
 * @create: 2018/6/25
 */
@Component
@Slf4j
public class RedisLockManager {

    @Autowired
    private JedisPool jedisPool;

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
    public Lock acquire(String key, String value, int expireTime) throws LockException {
        try (Jedis jedis = jedisPool.getResource()) {
            String result = jedis.set(key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            if (LOCK_SUCCESS.equals(result)) {
                return new Lock(this, key, value);
            }
            throw new LockException(key);
        }
    }

    public Lock acquire(String key, String value) throws LockException {
        return this.acquire(key, value, DEFAULT_MAX_WAITING_LOCK_MILLS);
    }

    /**
     * release lock
     *
     * @param lock
     * @throws LockException
     */
    public void release(Lock lock) throws LockException {
        try (Jedis jedis = jedisPool.getResource()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object res = jedis.eval(script, Collections.singletonList(lock.getKey()), Collections.singletonList(lock.getValue()));
            if (!RELEASE_SUCCESS.equals(res)) {
                throw new LockException(lock.getKey());
            }
        }
    }
}

