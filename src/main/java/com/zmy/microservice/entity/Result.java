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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zmy
 * @create: 2018/6/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private int code;

    private String msg;

    private Object data;

    public static Result success(Object src) {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), src);
    }

    public static Result success(int code, String msg, Object src) {
        return new Result(code, msg, src);
    }

    public static Result fail(ErrorCode errorCode) {
        return new Result(errorCode.getCode(), errorCode.getMsg(), null);
    }

    public static Result fail(int code, String msg) {
        return new Result(code, msg, null);
    }

    /**
     * return object paging
     * @param count
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Result page(int count, List<T> list) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("count", count);
        map.put("list", list);
        return success(map);
    }

}
