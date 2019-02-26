/**
 * @(#)HbaseTemplate.java 1.0 2017年9月20日
 *
 * Copyright (c) 2017, YUNXI. All rights reserved.
 * YUNXI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.zlg.bs.dscenteritemservice.es;


import com.zlg.bs.dscenteritemservice.constants.NumConstrant;
import com.zlg.bs.dscenteritemservice.constants.SearchConstrant;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * ES模板类
 * 
 * @author tan.jie
 *
 */
@Getter
@Setter
public class ESTemplate implements ESOperations {

	private static final Logger LOGGER = LoggerFactory.getLogger(ESTemplate.class);
	private TransportClient client;
	private Long totalSize;

	public ESTemplate(ESClient esClient) throws UnknownHostException {
		this.client = esClient.getClient();
	}

	public TransportClient getClient() {
		return client;
	}

	/**
	 * 查询方法
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            类型名称
	 * @param mapper
	 *            映射接口实现类
	 * @param pageNum
	 *            分页号
	 * @param pageSize
	 *            分页大小
	 * @return
	 */
	public <T> List<T> query(String index, String type, RowMapper<T> mapper, Integer pageNum, Integer pageSize) {
		LOGGER.info("Enter into query method. index={}, type={}", index, type);
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.QUERY_THEN_FETCH);
		SearchResponse searchResponse = null;
		if (pageNum != null && pageSize != null) {
			searchResponse = searchRequestBuilder.setFrom((pageNum - 1) * pageSize).setSize(pageSize).get();
		} else {
			searchResponse = searchRequestBuilder.get();
		}
		SearchHit[] hits = searchResponse.getHits().getHits();
		setTotalSize(searchResponse);
		List<T> rs = new ArrayList<T>();
		for (SearchHit searchHit : hits) {
			rs.add(mapper.mapRow(searchHit));
		}
		return rs;
	}

	/**
	 * 通过DSL条件查询方法
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            类型名称
	 * @param queryBuilder
	 *            dslQuery语句构造类
	 * @param mapper
	 *            映射接口实现类
	 * @return
	 */
	public <T> List<T> queryWithFilter(String index, String type, QueryBuilder queryBuilder, RowMapper<T> mapper) {
		LOGGER.info("Enter into queryWithFilter method. index={}, type={}", index, type);
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.QUERY_THEN_FETCH);
        LOGGER.info("queryBuilder to JSON :{}",queryBuilder.toString());
		searchRequestBuilder.setQuery(queryBuilder);
		SearchResponse searchResponse = searchRequestBuilder.get();

		SearchHit[] hits = searchResponse.getHits().getHits();
		setTotalSize(searchResponse);
		List<T> rs = new ArrayList<T>();

		for (SearchHit searchHit : hits) {
			rs.add(mapper.mapRow(searchHit));
		}
		return rs;
	}

	/**
	 * 通过DSL条件查询方法
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            类型名称
	 * @param queryBuilder
	 *            dslQuery语句构造类
	 * @param pageNum
	 *            分页号
	 * @param pageSize
	 *            分页大小
	 * @param mapper
	 *            映射接口实现类
	 * @return
	 */
	public <T> List<T> queryWithFilter(String index, String type, QueryBuilder queryBuilder, Integer pageNum,
			Integer pageSize, RowMapper<T> mapper) {
		LOGGER.info("Enter into queryWithFilter method. index={}, type={}, pageNum={}, pageSize={}", index, type,
				pageNum, pageSize);
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.QUERY_THEN_FETCH);
        LOGGER.info("queryBuilder to JSON :{}",queryBuilder.toString());
		searchRequestBuilder.setQuery(queryBuilder);
		SearchResponse searchResponse ;
		if (pageNum != null && pageSize != null) {
			searchResponse = searchRequestBuilder.setFrom((pageNum - 1) * pageSize).setSize(pageSize).get();
		} else {
			searchResponse = searchRequestBuilder.get();
		}

		SearchHit[] hits = searchResponse.getHits().getHits();
		setTotalSize(searchResponse);
		List<T> rs = new ArrayList<T>();
		for (SearchHit searchHit : hits) {
			rs.add(mapper.mapRow(searchHit));
		}
		return rs;
	}

	/**
	 * 通过DSL条件查询方法
	 *
	 * @param index
	 *            索引名称
	 * @param type
	 *            类型名称
	 * @param queryBuilder
	 *            dslQuery语句构造类
	 * @param pageNum
	 *            分页号
	 * @param pageSize
	 *            分页大小
	 * @param sortField
	 * 			  排序字段
	 * @param  sortOrder
	 * 			  排序方式
	 * @param mapper
	 *            映射接口实现类
	 * @return
	 */
	public <T> List<T> queryWithFilter(String index, String type, QueryBuilder queryBuilder, Integer pageNum,
									   Integer pageSize,String sortField,
									   SortOrder sortOrder, RowMapper<T> mapper) {
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.QUERY_THEN_FETCH);
		searchRequestBuilder.setQuery(queryBuilder);
		searchRequestBuilder.addSort(sortField,sortOrder);
		SearchResponse searchResponse ;
		if (pageNum != null && pageSize != null) {
			searchResponse = searchRequestBuilder.setFrom((pageNum - 1) * pageSize).setSize(pageSize).get();
		} else {
			searchResponse = searchRequestBuilder.get();
		}

		SearchHit[] hits = searchResponse.getHits().getHits();
		setTotalSize(searchResponse);
		List<T> rs = new ArrayList<T>();
		for (SearchHit searchHit : hits) {
			rs.add(mapper.mapRow(searchHit));
		}
		return rs;
	}

	/**
	 * 通过DSL条件查询方法
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            类型名称
	 * @param queryBuilder
	 *            dslQuery语句构造类
	 *            映射接口实现类
	 * @return
	 */
	public SearchHit[] queryWithFilter(String index, String type, QueryBuilder queryBuilder) {
		LOGGER.info("Enter into queryWithFilter method. index={}, type={}", index, type);
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.QUERY_THEN_FETCH);
        LOGGER.info("queryBuilder to JSON :{}",queryBuilder.toString());
		searchRequestBuilder.setQuery(queryBuilder);
        searchRequestBuilder.setSize(NumConstrant.MAX_PAGE_SIZE);
		SearchResponse searchResponse = searchRequestBuilder.get();
		return searchResponse.getHits().getHits();
	}

	/**
	 * 通过DSL条件聚合查询方法
	 * @param index 索引名称
	 * @param type 类型名称
	 * @param queryBuilder dslQuery语句构造类
	 * @param aggregationBuilder 聚合
	 * @return
	 */
	public SearchResponse queryWithFilter(String index, String type, QueryBuilder queryBuilder,AggregationBuilder aggregationBuilder) {
		//TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("group_name").field("name")
		//		.subAggregation(AggregationBuilders.sum("sum_age").field("age"));
		SearchResponse searchResponse = client.prepareSearch(index).setTypes(type)
				.setQuery(queryBuilder)
				.setSearchType(SearchType.QUERY_THEN_FETCH)
				.addAggregation(aggregationBuilder)
				.get();
		return searchResponse;
	}

	/**
	 * 通过DSL条件查询方法
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            类型名称
	 * @param queryBuilder
	 *            dslQuery语句构造类
	 * @param sortField
	 *            排序字段名
	 * @param sortType
	 *            排序类型，"ASC"代表升序，"DESC"代码降序
	 * @param pageNum
	 *            分页号
	 * @param pageSize
	 *            分页大小
	 * @param mapper
	 *            映射接口实现类
	 * @return
	 */
	public <T> List<T> pagingAndSortQuery(String index, String type, QueryBuilder queryBuilder, String sortField,
			String sortType, Integer pageNum, Integer pageSize, RowMapper<T> mapper) {
		LOGGER.info("Enter into pagingAndSortQuery method. index={}, type={}, sortField={}, sortType={}, pageNum={}, pageSize={}",
				index, type, sortField, sortType, pageNum, pageSize);
		SearchRequestBuilder searchRequestBuilder ;
        sortType = StringUtils.upperCase(sortType);
		if (sortType.equals("ASC")) {
			searchRequestBuilder = client.prepareSearch(index).setTypes(type).setSearchType(SearchType.QUERY_THEN_FETCH)
					.addSort(sortField, SortOrder.ASC);
		} else {
			searchRequestBuilder = client.prepareSearch(index).setTypes(type).setSearchType(SearchType.QUERY_THEN_FETCH)
					.addSort(sortField, SortOrder.DESC);
		}

		if (queryBuilder != null) {
		    LOGGER.info("queryBuilder to JSON :{}",queryBuilder.toString());
			searchRequestBuilder.setQuery(queryBuilder);
		}

		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageNum - 1) * pageSize).setSize(pageSize).get();
		SearchHit[] hits = searchResponse.getHits().getHits();
		setTotalSize(searchResponse);
		List<T> rs = new ArrayList();
		for (SearchHit searchHit : hits) {
			rs.add(mapper.mapRow(searchHit));
		}
		return rs;
	}

	/**
	 * 通过DSL条件查询方法 (这里其实是会默认分页的)
	 *
	 * @param index
	 *            索引名称
	 * @param type
	 *            类型名称
	 * @param queryBuilder
	 *            dslQuery语句构造类
	 * @param sortField
	 *            排序字段名
	 * @param sortType
	 *            排序类型，"ASC"代表升序，"DESC"代码降序
	 * @param mapper
	 *            映射接口实现类
	 * @return
	 */
	public <T> List<T> queryAndSortQueryNotPage(String index, String type, QueryBuilder queryBuilder, String sortField,
			String sortType, RowMapper<T> mapper) {
		LOGGER.info("Enter into pagingAndSortQuery method. index={}, type={}, sortField={}, sortType={}", index, type,
				sortField, sortType);
		SearchRequestBuilder searchRequestBuilder ;
		if (sortType.equals("ASC")) {
			searchRequestBuilder = client.prepareSearch(index).setTypes(type).setSearchType(SearchType.QUERY_THEN_FETCH)
					.addSort(sortField, SortOrder.ASC);
		} else {
			searchRequestBuilder = client.prepareSearch(index).setTypes(type).setSearchType(SearchType.QUERY_THEN_FETCH)
					.addSort(sortField, SortOrder.DESC);
		}
		if (queryBuilder != null) {
            LOGGER.info("queryBuilder to JSON :{}",queryBuilder.toString());
			searchRequestBuilder.setQuery(queryBuilder);
		}

		SearchResponse searchResponse = searchRequestBuilder.get();
		SearchHit[] hits = searchResponse.getHits().getHits();
		setTotalSize(searchResponse);
		List<T> rs = new ArrayList<T>();
		for (SearchHit searchHit : hits) {
			rs.add(mapper.mapRow(searchHit));
		}
		return rs;
	}

	/**
	 * 通过DSL条件查询方法
	 * 
	 * @param index
	 *            索引
	 * @param type
	 *            类型
	 * @param queryBuilder
	 *            DSL查询语句
	 * @param pageNum
	 *            页号
	 * @param pageSize
	 *            页大小
	 * @return 索引原始数据
	 */
	public SearchHit[] queryWithFilter(String index, String type, QueryBuilder queryBuilder, Integer pageNum,
			Integer pageSize) {
		LOGGER.info("Enter into queryWithFilter method. index={}, type={}", index, type);
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.QUERY_THEN_FETCH);
		LOGGER.info("queryBuilder to Json {}",queryBuilder.toString());
		searchRequestBuilder.setQuery(queryBuilder);
		searchRequestBuilder.setFrom(pageNum - 1);
		searchRequestBuilder.setSize(pageSize);
		SearchResponse searchResponse = searchRequestBuilder.get();
		setTotalSize(searchResponse);
		return searchResponse.getHits().getHits();
	}

	/**
	 * 更新索引
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            索引类型
	 * @param docId
	 *            文档Id
	 * @param source
	 */
	public void updateIndex(String index, String type, String docId, Map<String, Object> source) {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(index);
		updateRequest.type(type);
		updateRequest.id(docId);
		updateRequest.doc(source);
		try {
			client.update(updateRequest).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新索引
	 * 
	 * @param index
	 * @param type
	 * @param docId
	 * @param script
	 */
	public void updateIndex(String index, String type, String docId, Script script) {
		UpdateRequest updateRequest = new UpdateRequest(index, type, docId).script(script);
		try {
			client.update(updateRequest).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setTotalSize(SearchResponse response) {
		totalSize = response.getHits().getTotalHits();
	}

	public Long count(String index, String type, QueryBuilder queryBuilder) {
		return client.prepareSearch(index).setTypes(type).setSize(0).setQuery(queryBuilder).get().getHits()
				.getTotalHits();
	}

	/**
	 * 构建索引
	 */
	public void index(String index, String type, String id, XContentBuilder contentBuilder) {
		client.prepareIndex(index, type, id).setSource(contentBuilder).get();
	}

	/**
	 * 构建索引
	 */
	public void index(String index, String type, String id,String routingId, XContentBuilder contentBuilder) {
		client.prepareIndex(index, type, id).setRouting(routingId).setParent(routingId).setSource(contentBuilder).get();
	}

	/**
	 * 通过查询更新
	 * 
	 * @param index
	 * @param type
	 * @param id
	 * @param queryBuilder
	 */
	public void updateByQuery(String index, String type, String id, Script script, QueryBuilder queryBuilder) {
		UpdateByQueryRequestBuilder ubqrb = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
		ubqrb.source(index).source().setTypes(type);
		ubqrb.script(script).filter(queryBuilder).get();
	}

	/**
	 * 通过DSL条件查询方法 支持多个排序
	 *
	 * @param index
	 *            索引名称
	 * @param type
	 *            类型名称
	 * @param queryBuilder
	 *            dslQuery语句构造类
	 * @param sortMap
	 *            排序map
	 * @param pageNum
	 *            分页号
	 * @param pageSize
	 *            分页大小
	 * @param mapper
	 *            映射接口实现类
	 * @return
	 */
	public <T> List<T> pagingAndSortQuery(String index, String type, QueryBuilder queryBuilder, Map<String,SortOrder> sortMap,
										   Integer pageNum, Integer pageSize, RowMapper<T> mapper) {
		LOGGER.info(
				"Enter into pagingAndSortQuery method. index={}, type={}, sortField={}, sortType={}, pageNum={}, pageSize={}",
				index, type, sortMap, pageNum, pageSize);
		SearchRequestBuilder searchRequestBuilder ;
		searchRequestBuilder = client.prepareSearch(index).setTypes(type).setSearchType(SearchType.QUERY_THEN_FETCH);
		for(Map.Entry<String,SortOrder> entry:sortMap.entrySet()){
			searchRequestBuilder.addSort(entry.getKey(), entry.getValue());
		}
		if (queryBuilder != null) {
            LOGGER.info("queryBuilder to JSON :{}",queryBuilder.toString());
			searchRequestBuilder.setQuery(queryBuilder);
		}

		SearchResponse searchResponse = searchRequestBuilder.setFrom((pageNum - 1) * pageSize).setSize(pageSize).get();
		SearchHit[] hits = searchResponse.getHits().getHits();
		setTotalSize(searchResponse);
		List<T> rs = new ArrayList();
		for (SearchHit searchHit : hits) {
			rs.add(mapper.mapRow(searchHit));
		}
		return rs;
	}


	/**
	 * 通过DSL条件查询方法
	 *
	 * @param index
	 *            索引名称
	 * @param type
	 *            类型名称
	 *            dslQuery语句构造类
	 * @param pageNum
	 *            分页号
	 * @param pageSize
	 *            分页大小
	 * @return

	public SearchResponse queryWithAgg(String index, String type, Map<String,QueryBuilder> queryBuilderMap, AggregationBuilder aggregationBuilder ,
									Integer pageNum,Integer pageSize) {
		LOGGER.info("Enter into queryWithFilter method. index={}, type={}, pageNum={}, pageSize={}", index, type,
				pageNum, pageSize);
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type)
				.setSearchType(SearchType.QUERY_THEN_FETCH);

        if(MapUtils.isNotEmpty(queryBuilderMap)){
            if(queryBuilderMap.get(SearchConstrant.QUERY) !=null){
                LOGGER.info("QueryBuilder query to JSON :{}",queryBuilderMap.get(SearchConstrant.QUERY).toString());
                searchRequestBuilder.setQuery(queryBuilderMap.get(SearchConstrant.QUERY));
            }
            if(queryBuilderMap.get(SearchConstrant.POST)!=null){
                LOGGER.info("QueryBuilder post to JSON :{}",queryBuilderMap.get(SearchConstrant.POST).toString());
                searchRequestBuilder.setPostFilter(queryBuilderMap.get(SearchConstrant.POST));
            }
        }
        LOGGER.info("aggregationBuilder to JSON :{}",aggregationBuilder.toString());
		searchRequestBuilder.addAggregation(aggregationBuilder);
		SearchResponse searchResponse ;
		if (pageNum != null && pageSize != null) {
			searchResponse = searchRequestBuilder.setFrom((pageNum - 1) * pageSize).setSize(pageSize).execute().actionGet();
		} else {
			searchResponse = searchRequestBuilder.get();
		}
		return  searchResponse;
    }
     */

    /**
     * 通过DSL条件查询方法
     *
     * @param index
     *            索引名称
     * @param type
     *            类型名称
     *            dslQuery语句构造类
     * @param pageNum
     *            分页号
     * @param pageSize
     *            分页大小
     * @return
     */
    public SearchResponse queryWithAggAndSort(String index, String type, Map<String,QueryBuilder> queryBuilderMap,
                                              AggregationBuilder aggregationBuilder, Map<String,SortOrder> sortMap,
                                              Integer pageNum, Integer pageSize) {
        LOGGER.info("Enter into queryWithFilter method. index={}, type={}, pageNum={}, pageSize={}", index, type,
                pageNum, pageSize);
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type)
                .setSearchType(SearchType.QUERY_THEN_FETCH);
        if(MapUtils.isNotEmpty(queryBuilderMap)){
            if(queryBuilderMap.get(SearchConstrant.QUERY) !=null){
                LOGGER.info("QueryBuilder query to JSON :{}",queryBuilderMap.get(SearchConstrant.QUERY).toString());
                searchRequestBuilder.setQuery(queryBuilderMap.get(SearchConstrant.QUERY));
            }
            if(queryBuilderMap.get(SearchConstrant.POST)!=null){
                LOGGER.info("QueryBuilder post to JSON :{}",queryBuilderMap.get(SearchConstrant.POST).toString());
                searchRequestBuilder.setPostFilter(queryBuilderMap.get(SearchConstrant.POST));
            }
        }
        if(MapUtils.isNotEmpty(sortMap)){
            for(Map.Entry<String,SortOrder> entry:sortMap.entrySet()){
                searchRequestBuilder.addSort(entry.getKey(), entry.getValue());
            }
        }
        LOGGER.info("aggregationBuilder to JSON :{}",aggregationBuilder.toString());
        searchRequestBuilder.addAggregation(aggregationBuilder);
        SearchResponse searchResponse ;
        if (pageNum != null && pageSize != null) {
            searchResponse = searchRequestBuilder.setFrom((pageNum - 1) * pageSize).setSize(pageSize).execute().actionGet();
        } else {
            searchResponse = searchRequestBuilder.get();
        }
        return  searchResponse;
    }



    public  <T> List<T> mapRowResult(SearchResponse searchResponse ,RowMapper<T> mapper){
        SearchHit[] hits = searchResponse.getHits().getHits();
        List<T> result = new ArrayList();
        for (SearchHit searchHit : hits) {
            result.add(mapper.mapRow(searchHit));
        }
        setTotalSize(searchResponse);
        return  result;
    }

	/**
	 * 删除文档
	 * @param index
	 * @param type
	 * @param id
	 */
    public void deleteDocument(String index,String type,String id,String routing){
        DeleteResponse response = this.client.prepareDelete(index, type, id).setRouting(routing).get();
    }

}
