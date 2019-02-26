/**
 * @(#)Constants.java 1.0 2017年12月12日
 *
 * Copyright (c) 2016, YUNXI. All rights reserved.
 * YUNXI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.zlg.bs.dscenteritemservice.common;

/**
 * @author tan.jie
 *
 */
public interface Constants {
	// elasticsearch config
	String ELASTICSEARCH_REGISTRYVO = "ds.search.application.es.registryvo";
	String ELASTICSEARCH_INDEXVO = "ds.search.application.es.indexvo";
	String INDEX_REGISTRYVO = "ds.search.index.registryvo";
	String INDEX_ITEM = "item";
	String INDEX_ORDER = "order";
	String INDEX_USER_ORDER = "userOrder";
	String INDEX_USER_ITEM = "userItem";

	// sort
	String SORT_COMPREHENSIVE = "comprehensive";
	String SORT_SALES_COUNT = "salescount";
	String SORT_COMMENT_COUNT = "commentcount";
	String SORT_PRICE = "price";
	String SORT_TYPE_ASC = "ASC";
	String SORT_TYPE_DESC = "DESC";

}
