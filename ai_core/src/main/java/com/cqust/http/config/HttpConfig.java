package com.cqust.http.config;

import lombok.Data;

import java.util.Map;

@Data
public class HttpConfig {

    // 连接池最大连接数
    private Integer maxConnection = 50;

    // 从连接池获取连接的最大等待时间 单位:秒
    private Integer pendingAcquireTimeout = 5;

    // 空闲连接存活时间 单位:秒
    private Integer maxIdleTime = 30;

    // TCP 连接超时 单位:毫秒
    private Integer connectTime = 5000;

    // 从已建立连接上等待数据的最大时间 单位:秒
    private Integer socketTime = 5;

    // 读超时 单位:秒
    private Integer readTime = 5;

    // 写超时 单位:秒
    private Integer writeTime = 5;

    // 默认header
    private Map<String, String> headerMap;

}
