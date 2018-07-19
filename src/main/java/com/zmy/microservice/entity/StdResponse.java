package com.zmy.microservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: zmy
 * @create: 2018/6/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StdResponse {

    private int code;

    private String msg;

    private Object data;

    public static StdResponse success(Object src) {
        return new StdResponse(200, ResultCode.SUCCESS.getMsg(), src);
    }

    public static StdResponse success(int code, String msg, Object src) {
        return new StdResponse(code, msg, src);
    }

    public static StdResponse fail(ErrorCode errorCode) {
        return new StdResponse(errorCode.getCode(), errorCode.getMsg(), null);
    }

    public static StdResponse fail(int code, String msg) {
        return new StdResponse(code, msg, null);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class PageResponse<T> {
        private long count;

        private List<T> list;
    }

    /**
     * return object paging
     *
     * @param count
     * @param list
     * @param <T>
     * @return
     */


    public static <T> StdResponse page(int count, List<T> list) {
        PageResponse<T> pageResponse = new PageResponse<>(count, list);
        return success(pageResponse);
    }

}
