package com.zmy.microservice.util;

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

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

import static org.springframework.util.StringUtils.hasText;

/**
 * @author: zmy
 * @create: 2018/6/25
 */
@Slf4j
public class NetworkUtils {

    /**
     * get  remote real ip
     *
     * @param request
     * @return
     */
    private static String realIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Real-IP");
        log.debug("X-Real-IP is {}", ip);

        if (!hasText(ip)) {
            ip = request.getHeader("REMOTE-HOST");
            log.debug("REMOTE-HOST is {}", ip);
        }

        if (!hasText(ip)) {
            ip = request.getHeader("x-forwarded-for");
            log.debug("x-forwarded-for is {}", ip);
        }

        if (!hasText(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.debug("Proxy-Client-IP is {}", ip);
        }

        if (!hasText(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.debug("WL-Proxy-Client-IP is {}", ip);
        }

        if (!hasText(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.debug("HTTP_CLIENT_IP is {}", ip);
        }

        if (!hasText(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.debug("HTTP_X_FORWARDED_FOR is {}", ip);
        }

        if (hasText(ip)) {
            StringTokenizer st = new StringTokenizer(ip, ",");
            // 多级反向代理时，取第一个IP
            if (st.countTokens() > 1) {
                ip = st.nextToken();
            }
        } else {
            // 未获取到反向代理IP，返回RemoteAddr
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
