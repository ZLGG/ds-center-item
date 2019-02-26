/**
 * @(#)HbaseOperations.java 1.0 2017年9月20日
 *
 * Copyright (c) 2017, YUNXI. All rights reserved.
 * YUNXI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.zlg.bs.dscenteritemservice.es;

import org.elasticsearch.client.transport.TransportClient;

/**
 * ES操作类
 * 
 * @author tan.jie
 *
 */
public interface ESOperations {

	TransportClient getClient();

}
