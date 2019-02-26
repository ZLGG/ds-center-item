/**
 * @(#)ESClient.java 1.0 2017年10月26日
 * <p>
 * Copyright (c) 2017, YUNXI. All rights reserved.
 * YUNXI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.zlg.bs.dscenteritemservice.es;

import lombok.Data;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 封装ES的TransportClient类
 *
 * @author tan.jie
 */
@Data
public class ESClient {

    private static final String ALIYUN_ES = "aliyunes";
    private static final String ES = "es";

    private String cluster;
    private String appKey;
    private String appSecret;
    private String host;
    private Integer port;
    private Boolean sniff;

    public ESClient(String cluster, String appKey, String appSecret, String host, Integer port, Boolean sniff) {
        this.cluster = cluster;
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.host = host;
        this.port = port;
        this.sniff = sniff;
    }

    public TransportClient getClient() throws UnknownHostException {

        Settings.Builder builder = Settings.builder().put("cluster.name", cluster);
            builder.put("xpack.security.user", appKey + ":" + appSecret);
            builder.put("client.transport.sniff", sniff);
        Settings settings = builder.build();
        TransportClient client = new PreBuiltXPackTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        return client;
    }
}
