package com.zmy.microservice.autoconfig;

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

import com.zmy.microservice.redis.JedisProperties;
import com.zmy.microservice.redis.RedisLockManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: zmy
 * @create: 2018/6/25
 */
@Configuration
@ConditionalOnClass(RedisLockManager.class)
@EnableConfigurationProperties(JedisProperties.class)
public class RedisAutoConfiguration {

    @Autowired
    private JedisProperties properties;

    @Bean
    public RedisLockManager redisManager() {
        return new RedisLockManager(jedisPool());
    }

    private JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(properties.getMaxIdle());
        config.setTestOnBorrow(properties.isTestOnBorrow());
        config.setMinIdle(properties.getMinIdle());
        config.setMaxTotal(properties.getMaxTotal());
        config.setMaxWaitMillis(properties.getMaxWaitMillis());
        config.setNumTestsPerEvictionRun(properties.getNumTestsPerEvictionRun());
        config.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        config.setTestOnReturn(properties.isTestOnReturn());
        return new JedisPool(config, properties.getIp(), properties.getPort());
    }
}
