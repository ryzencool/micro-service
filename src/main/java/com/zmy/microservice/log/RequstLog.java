package com.zmy.microservice.log;


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
