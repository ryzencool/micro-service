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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @description local date time util
 * @author: zmy
 * @create: 2018/6/20
 */
public class DateUtils {

    private static final String DEFAULT_PATTERN = "yyyyMMddHHmmss";

    /**
     * get current time
     *
     * @return the text string of current time, not null
     */
    public static String currentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
    }

    /**
     * obtain an instance of LocalDateTime from time string using a specific pattern
     *
     * @param time    the time string to use, not null
     * @param pattern the pattern to use,not null
     * @return the parsed local-date-time, not null
     */
    public static LocalDateTime parse(String time, String pattern) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * obtain an instance of LocalDateTime from time string using a default pattern{@code DEFAULT_PATTERN}
     *
     * @param time the time string to use, not null
     * @return the parsed local-date-time, not null
     */
    public static LocalDateTime parse(String time) {
        return parse(time, DEFAULT_PATTERN);
    }

    /**
     * format local-date-time to string by specific pattern
     *
     * @param time    the local-date-time to use, not null
     * @param pattern the pattern to use, not null
     * @return the formatted-time string, not null
     */
    public static String format(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * format local-date-time to string by default pattern{@code DEFAULT_PATTERN}
     *
     * @param time the local-date-time to use, not null
     * @return the formatted time string, not null
     */
    public static String format(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
    }


}
