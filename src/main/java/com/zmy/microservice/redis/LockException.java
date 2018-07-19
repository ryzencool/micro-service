package com.zmy.microservice.redis;


import lombok.Getter;
import lombok.Setter;

/**
 * @author: zmy
 * @create: 2018/6/25
 */
public class LockException extends Exception {

    @Getter
    @Setter
    private String key;

    public LockException(String key) {
        super(String.format("cannot lock: [%s]", String.join(",", key)));
        this.key = key;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }

}
