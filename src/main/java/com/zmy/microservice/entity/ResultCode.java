package com.zmy.microservice.entity;


/**
 * @description: 错误码结果，继承该类
 * @author: zmy
 * @create: 2018/6/25
 */
public class ResultCode {

    public static final ErrorCode SUCCESS = new ErrorCode(200, "success");

    public static final ErrorCode FAIL = new ErrorCode(400, "fail");

}
