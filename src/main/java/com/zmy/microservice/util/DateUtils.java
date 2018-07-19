package com.zmy.microservice.util;


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
