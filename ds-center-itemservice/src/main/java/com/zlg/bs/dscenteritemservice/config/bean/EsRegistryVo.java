/**
 * @(#) EsRegistryVo.java 1.0 2017-12-28
 * Copyright (c) 2017, YUNXI. All rights reserved.
 * YUNXI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.zlg.bs.dscenteritemservice.config.bean;

/**
 * @author 风行
 * @since 0.5.0
 */
public class EsRegistryVo {

    private String cluster;
    private String appKey;
    private String appSecret;
    private String host;
    private Integer port;
    private Boolean sniff;

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getSniff() {
        return sniff;
    }

    public void setSniff(Boolean sniff) {
        this.sniff = sniff;
    }
}
