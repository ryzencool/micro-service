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
import com.zmy.microservice.util.UUIDGen;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static net.logstash.logback.marker.Markers.append;

/**
 * @description: request log interceptor
 * @author: zmy
 * @create: 2018/6/25
 */
@Slf4j
public class RequestLogInterceptor implements HandlerInterceptor {

    public static final String UUID_KEY = "uuId";

    public static final String SESSION_ID_KEY = "sessionId";

    public static final String INVOKE_TIME_KEY = "invokeTime";

    public static final String BEGIN_TIME_KEY = "beginTime";

    public static final String COST_TIME_KEY = "costTime";

    public static final String METHOD_KEY = "methodName";

    public static final String PARAMS_KEY = "paramValues";

    public static final String RESULT_KEY = "resultValue";

    public static final String EXCEPTION_MSG_KEY = "exceptionMsg";

    public static final String REQUEST_REMOTE_HOST_MDC_KEY = "senderHost";

    public static final String REQUEST_USER_AGENT_MDC_KEY = "userAgent";

    public static final String REQUEST_REQUEST_URI = "req.requestURI";

    public static final String REQUEST_QUERY_STRING = "req.queryString";

    public static final String REQUEST_REQUEST_URL = "req.requestURL";

    public static final String REQUEST_X_FORWARDED_FOR = "req.xForwardedFor";


    public static final String LOGED_FLAG = "$LOGED_FLAG$";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        addMdc(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        calcCostTime();
        log.info(append(PARAMS_KEY, request.getParameterMap()).and(append(RESULT_KEY, modelAndView.getModelMap())), "");
        MDC.put(LOGED_FLAG, String.valueOf(true));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null || MDC.get(LOGED_FLAG) == null) {
            calcCostTime();
            if (ex != null) {
                MDC.put(EXCEPTION_MSG_KEY, ex.getLocalizedMessage());
            }
            log.error(append(PARAMS_KEY, request.getParameterMap()), "request fail.");

        }
        clearMdc();
    }

    /**
     * add to log mdc
     *
     * @param request
     */
    private void addMdc(HttpServletRequest request) {
        MDC.put(INVOKE_TIME_KEY, DateUtils.currentTime());
        MDC.put(UUID_KEY, UUIDGen.uuid());
        MDC.put(BEGIN_TIME_KEY, String.valueOf(System.currentTimeMillis()));
        MDC.put(SESSION_ID_KEY, request.getRequestedSessionId());
        MDC.put(METHOD_KEY, request.getMethod());
        MDC.put(REQUEST_REMOTE_HOST_MDC_KEY, request.getRemoteHost());
        MDC.put(REQUEST_REQUEST_URI, request.getRequestURI());
        StringBuffer sb = request.getRequestURL();
        if (sb != null) {
            MDC.put(REQUEST_REQUEST_URL, sb.toString());
        }
        MDC.put(REQUEST_QUERY_STRING, request.getQueryString());
        MDC.put(REQUEST_USER_AGENT_MDC_KEY, request.getHeader("User-Agent"));
        MDC.put(REQUEST_X_FORWARDED_FOR, request.getHeader("X-Forwarded-For"));
    }

    /**
     * clear properties from mdc
     */
    private void clearMdc() {
        MDC.remove(INVOKE_TIME_KEY);
        MDC.remove(UUID_KEY);
        MDC.remove(BEGIN_TIME_KEY);
        MDC.remove(SESSION_ID_KEY);
        MDC.remove(METHOD_KEY);
        MDC.remove(COST_TIME_KEY);
        MDC.remove(REQUEST_REMOTE_HOST_MDC_KEY);
        MDC.remove(REQUEST_REQUEST_URI);
        MDC.remove(REQUEST_QUERY_STRING);

        MDC.remove(REQUEST_REQUEST_URL);
        MDC.remove(REQUEST_USER_AGENT_MDC_KEY);
        MDC.remove(REQUEST_X_FORWARDED_FOR);
        MDC.remove(EXCEPTION_MSG_KEY);
    }

    /**
     * calculate time cost between request and response
     */
    private void calcCostTime() {
        long costTime = -1l;
        try {
            costTime = System.currentTimeMillis() - Long.valueOf(MDC.get(BEGIN_TIME_KEY));
        } catch (Exception e) {
            // ignore
        }
        MDC.put(COST_TIME_KEY, String.valueOf(costTime));
    }
}
