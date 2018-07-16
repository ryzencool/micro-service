package com.zmy.microservice.entity;

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
        return new StdResponse(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), src);
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
