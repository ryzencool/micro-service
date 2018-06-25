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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: zmy
 * @create: 2018/6/25
 */

@Configuration
@Slf4j
@ConfigurationProperties("redis.lock.pool")
public class JedisConfig {

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

    private Integer port;


    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setMinIdle(minIdle);
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        config.setTestOnReturn(testOnReturn);
        return new JedisPool(config, ip, port);
    }

}
