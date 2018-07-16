package com.zmy.microservice.log;

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

import com.zmy.microservice.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author: zmy
 * @create: 2018/6/25
 */
@Data
public class RequstLog {

    private static String systemCurrentTime() {
        return DateUtils.currentTime();
    }

    private String invokeTime = systemCurrentTime();

    private String uid;

    private String sessionId;

    private String fromIp;

    private long cost;

    private Param param;

    private Result result;

    private String uri;

    private String mapping;

    private String siteOwner;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Param {

        private Map<String, String> header;

        private String queryStr;

        private String payload;

        private String paramMap;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        private int status;

        private String content;

        private String exceptionMsg;
    }

}
