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

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: the encapsulation of {@HttpClient}
 * @author: zmy
 * @create: 2018/6/21
 */
@Slf4j
public class HttpClientUtils {


    /**
     * default read timeout
     */
    public static final int DEFAULT_READ_TIMEOUT = 5000;

    /**
     * default connect timeout
     */
    public static final int DEFAULT_CONNECT_TIMEOUT = 3000;

    /**
     * default character encoding, utf-8
     */
    public static final String UTF_8 = "UTF-8";

    private HttpClientUtils() {
    }

    /**
     * consistent with http method get
     *
     * @param url
     * @param queryString    the url parameter
     * @param readTimeout
     * @param connectTimeout
     * @param coding
     * @return
     * @throws Exception
     */
    public static String get(String url, String queryString, int readTimeout, int connectTimeout, String coding) throws Exception {

        assert url != null : "url not null";

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(readTimeout)
                .setConnectionRequestTimeout(connectTimeout).build();

        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        String reqUrl = queryString == null || queryString.length() == 0 ? url : url + '?' + queryString;
        HttpGet getMethod = new HttpGet(reqUrl);

        HttpEntity httpEntity = null;

        try {
            HttpResponse response = httpClient.execute(getMethod);

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                log.error("get failed:{}", response.getStatusLine());
                throw new Exception(response.getStatusLine().toString());
            } else {
                httpEntity = response.getEntity();
                String returnMsg = null;

                if (httpEntity != null) {
                    returnMsg = EntityUtils.toString(httpEntity, coding);
                }
                return returnMsg;
            }
        } finally {
            getMethod.abort();
            try {
                EntityUtils.consume(httpEntity);
                httpClient.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }


    public static String get(String url, String queryString) throws Exception {
        return get(url, queryString, DEFAULT_READ_TIMEOUT, DEFAULT_CONNECT_TIMEOUT, UTF_8);
    }


    public static String get(String url, Map<String, String> queryMap) throws Exception {
        String queryString = null;
        if (queryMap != null) {
            StringBuilder sb = new StringBuilder();
            queryMap.forEach((k, v) -> {
                sb.append(k);
                sb.append("=");
                sb.append(v);
                sb.append("&");
            });
            queryString = sb.toString().substring(0, sb.length() - 1);
        }
        return get(url, queryString, DEFAULT_READ_TIMEOUT, DEFAULT_CONNECT_TIMEOUT, UTF_8);
    }

    public static String get(String url) throws Exception {
        return get(url, null, DEFAULT_READ_TIMEOUT, DEFAULT_CONNECT_TIMEOUT, UTF_8);
    }

    /**
     * consistent with http method post
     *
     * @param url
     * @param requestMap
     * @param readTimeout
     * @param connectTimeout
     * @param coding
     * @return
     * @throws Exception
     */
    public static String post(String url, Map<String, String> requestMap, int readTimeout, int connectTimeout, String coding)
            throws Exception {
        String returnMsg = "";

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(readTimeout)
                .setConnectionRequestTimeout(connectTimeout).build();

        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<>();

            for (Map.Entry<String, String> entry : requestMap.entrySet()) {
                String key = entry.getKey();
                nvps.add(new BasicNameValuePair(key, entry.getValue()));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, coding));
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + coding);
            httpPost.addHeader("Accept-Language", "zh-cn");

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                returnMsg = EntityUtils.toString(httpEntity, coding);
                EntityUtils.consume(httpEntity);
            }
            httpPost.abort();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // ignore
            }
        }
        return returnMsg;
    }

    public static String post(String url, Map<String, String> requestMap) throws Exception {
        return post(url, requestMap, DEFAULT_READ_TIMEOUT, DEFAULT_CONNECT_TIMEOUT, UTF_8);
    }


    /**
     * get request with param bean
     *
     * @param url
     * @param param
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> String getByBean(String url, T param) throws Exception {
        Map<String, String> map = objectReflect(param);
        return get(url, map);
    }

    /**
     * post request with param bean
     *
     * @param url
     * @param param
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> String postByBean(String url, T param) throws Exception {
        Map<String, String> map = objectReflect(param);
        return post(url, map);
    }

    /**
     * post request return bean， assume that response is json
     *
     * @param url
     * @param param
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T, R> R postRetBean(String url, T param, Class<R> rClass) throws Exception {
        Map<String, String> map = objectReflect(param);
        ObjectMapper mp = new ObjectMapper();
        return mp.readValue(post(url, map), rClass);
    }


    /**
     * get param is bean  and return value is bean
     *
     * @param url
     * @param param query param
     * @param <T>   query param bean class generic
     * @param <R>   response value bean class generic
     * @return
     * @throws Exception
     */
    public static <T, R> R getRetBean(String url, T param, Class<R> rClass) throws Exception {
        Map<String, String> map = objectReflect(param);
        ObjectMapper mp = new ObjectMapper();
        return mp.readValue(get(url, map), rClass);
    }

    /**
     * object reflect to map
     *
     * @param param
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    private static <T> Map<String, String> objectReflect(T param) throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        for (Field field : param.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(param).toString());
        }
        return map;
    }

}
