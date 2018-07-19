package com.zmy.microservice.redis;


import lombok.Data;

/**
 * @description: redis分布式锁
 * @author: zmy
 * @create: 2018/6/25
 */
@Data
public class RedisLock implements AutoCloseable {

    private RedisLockManager redisLockManager;

    private String key;

    private String value;

    public RedisLock(RedisLockManager redisLockManager, String key, String value) {
        this.redisLockManager = redisLockManager;
        this.key = key;
        this.value = value;
    }

    @Override
    public void close() throws Exception {
        redisLockManager.release(this);
    }


}
