package com.zlg.bs.dscenteritemservice.common;

import org.apache.commons.lang3.StringUtils;

/**
 * @(#) ElasticUtils 1.0 2018/5/3
 * <p>
 * YUNXI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class ElasticUtils {

    /**
     * 封装模糊查询（针对keyword字段）
     * @param param
     * @return
     */
    public static  String wrapWildcard(String param){
        return "*".concat(StringUtils.trim(param)).concat("*");
    }

    /**
     * 组装keyword
     * @param name
     * @return
     */
    public static  String wrapName(String name){
        return StringUtils.trim(name).concat(".keyword");
    }

    /**
     * 封装小写的模糊查询（针对非keyword字段）
     * @param param
     * @return
     */
    public static  String wrapLowerCaseWildcard(String param){
        return "*".concat(StringUtils.trim(param.toLowerCase())).concat("*");//通配符查询时传入字段需要转小写；但按字段的keyword子字段时不可转小写！！！
    }
}
