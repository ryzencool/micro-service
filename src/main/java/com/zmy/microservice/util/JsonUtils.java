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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @descpription: the encapsulation of {@ObjectMapper}
 * @author: zmy
 * @create: 2018/6/21
 */
@Slf4j
public class JsonUtils {

    public static <T> T stringToObject(String json, Class<T> tClass) {
        assert json != null : "json src must not be null";
        T t = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            t = mapper.readValue(json, tClass);
        } catch (IOException e) {
            // ignore
            log.error("json transfer to object failed");
        }
        return t;
    }

    public static <T> T stringToObject(String json, TypeReference<T> reference) {
        assert json != null : "json src must not be null";
        ObjectMapper mapper = new ObjectMapper();
        T t = null;
        try {
            t = mapper.readValue(json, reference);
        } catch (IOException e) {
            // ignore
            log.error("json transfer to object failed");
        }
        return t;
    }

    public static <T> String objectToString(T src) {
        assert src != null : "source must not be null";
        ObjectMapper mapper = new ObjectMapper();
        String target = null;
        try {
            target = mapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            // ignore
            log.error("object transfer to json failed");
        }
        return target;
    }
}
