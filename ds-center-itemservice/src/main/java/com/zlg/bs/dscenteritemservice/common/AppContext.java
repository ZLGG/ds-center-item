/**
 * @(#)AppContext.java 1.0 2017年11月28日
 *
 * Copyright (c) 2016, YUNXI. All rights reserved.
 * YUNXI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.zlg.bs.dscenteritemservice.common;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * 应用上下文
 * 
 * @author tan.jie
 *
 */
@Data
public class AppContext implements ApplicationContextAware {
	private static ApplicationContext context;

	private String item;// 商品索引
	private String member;// 会员索引
	private String order;// 订单索引
	private String userItem;// 用户商品映射关系索引
	private String inv; // 库存
    private String user; // 后台用户查询所有

	public static ApplicationContext getApplicationContext() {
		return context;
	}


	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
