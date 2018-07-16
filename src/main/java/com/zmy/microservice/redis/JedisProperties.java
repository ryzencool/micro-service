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
