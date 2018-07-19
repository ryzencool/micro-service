package com.zmy.microservice.util;

import java.util.UUID;


/**
 * @author: zmy
 * @create: 2018/6/20
 */
public class UUIDGen {

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String uuidUpper() {
        return uuid().toUpperCase();
    }

    public static String cleanUuid() {
        return uuid().replace("-", "");
    }

    public static String cleanUuidUpper() {
        return cleanUuid().toUpperCase();
    }

}
