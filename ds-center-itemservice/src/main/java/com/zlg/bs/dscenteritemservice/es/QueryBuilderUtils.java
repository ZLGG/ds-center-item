package com.zlg.bs.dscenteritemservice.es;

import com.zlg.bs.dscenteritemservice.common.ElasticUtils;
import org.elasticsearch.index.query.*;

/**
 * @Description TODO
 * @Author: wutian
 * @Date: 2018/10/30 15:42
 */
public class QueryBuilderUtils {
    /**
     * 设置name类型字段的通配符查询
     * @param boolQueryBuilder bool query
     * @param fieldName 字段名
     * @param name 字段待包含的字符串值
     */
    public static void setNameWildcardQuery(BoolQueryBuilder boolQueryBuilder, String fieldName, String name) {
        BoolQueryBuilder boolNameQuery = QueryBuilders.boolQuery();
        boolNameQuery.must(QueryBuilders.wildcardQuery(fieldName + ".keyword", ElasticUtils.wrapWildcard(name)));
        boolQueryBuilder.must(boolNameQuery);
    }
    /**
     * 设置name类型字段的分词模糊查询
     * @param boolQueryBuilder bool query
     * @param fieldName 字段名
     * @param name 字段待包含的字符串值
     */
    public static void setNameMatchQuery(BoolQueryBuilder boolQueryBuilder, String fieldName, String name) {
        BoolQueryBuilder boolNameQuery = QueryBuilders.boolQuery();
        boolNameQuery.must(QueryBuilders.matchQuery(fieldName, name));
        boolQueryBuilder.must(boolNameQuery);
    }
    /**
     * 设置name类型字段的通配符+分词模糊查询
     * @param boolQueryBuilder bool query
     * @param fieldName 字段名
     * @param name 字段待包含的字符串值
     */
    public static void setNameWildcardOrMatchQuery(BoolQueryBuilder boolQueryBuilder, String fieldName, String name) {
        BoolQueryBuilder boolNameQuery = QueryBuilders.boolQuery();
        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery(fieldName + ".keyword", ElasticUtils.wrapWildcard(name));
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(fieldName, name);

        boolNameQuery.should(wildcardQueryBuilder).should(matchQueryBuilder).minimumShouldMatch(1);
        boolQueryBuilder.must(boolNameQuery);
    }
}
