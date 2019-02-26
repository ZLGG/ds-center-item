/**
 * @(#)RowMapper.java 1.0 2017年12月12日
 *
 * Copyright (c) 2016, YUNXI. All rights reserved.
 * YUNXI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.zlg.bs.dscenteritemservice.es;

import org.elasticsearch.search.SearchHit;

/**
 * ES记录映射
 * 
 * @author tan.jie
 *
 */
public interface RowMapper<T> {
	T mapRow(SearchHit searchHit);
}
