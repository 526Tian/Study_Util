package com.cqust.http.config;

import lombok.Data;

@Data
public class HttpConfig {

    // 连接池最大连接数
    private Integer maxConnection;

    // 从连接池获取连接的最大等待时间 单位:秒
    private Integer pendingAcquireTimeout;

    // 空闲连接存活时间 单位:秒
    private Integer maxIdleTime;

    // TCP 连接超时 单位:毫秒
    private Integer connectTime;

    // 从已建立连接上等待数据的最大时间 单位:秒
    private Integer socketTime;

    // 读超时 单位:秒
    private Integer readTime;

    // 写超时 单位:秒
    private Integer writeTime;

}
