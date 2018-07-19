package com.zmy.microservice.redis;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: zmy
 * @create: 2018/6/25
 */

@Data
@ConfigurationProperties("redis.lock.pool")
public class JedisProperties {

    private int maxTotal;

    private int minIdle;

    private int maxWaitMillis;

    private boolean testOnBorrow;

    private boolean testOnReturn;

    private int timeBetweenEvictionRunsMillis;

    private boolean testWhileIdle;

    private int numTestsPerEvictionRun;

    private int maxIdle;

    private String ip;

    private int port;
}
