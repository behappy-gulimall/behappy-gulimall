package org.xiaowu.behappy.common.es.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.*;

/**
 * @author xiaowu
 */
public class EsUtils {

    private static final RestHighLevelClient REST_HIGH_LEVEL_CLIENT;

    static {
        REST_HIGH_LEVEL_CLIENT = SpringUtil.getBean("restHighLevelClient");
    }

    /**
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     * @throws IOException
     */
    public static boolean indexExists(String indexName) throws Exception {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        boolean exists = EsUtils.REST_HIGH_LEVEL_CLIENT.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        return exists;
    }

    /**
     * 根据条件删除
     * BoolQueryBuilder 搜索
     *
     * @param indexName
     */
    public static boolean delData(String indexName, BoolQueryBuilder boolQueryBuilder) throws Exception {
        try {
            DeleteByQueryRequest request = new DeleteByQueryRequest(indexName);
            //构建搜索条件
            request.setQuery(boolQueryBuilder);
            request.setTimeout(TimeValue.timeValueMinutes(10));
            BulkByScrollResponse resp = EsUtils.REST_HIGH_LEVEL_CLIENT.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 批量新增文档
     *
     * @param indexName
     * @param list
     */
    public static boolean addData(String indexName, List list) throws Exception {
        ObjectMapper objectMapper = SpringUtil.getBean("objectMapper");
        if (CollUtil.isNotEmpty(list)) {
            BulkRequest bulkRequest = new BulkRequest();
            for (Object calculateMetaData : list) {
                IndexRequest indexRequest = new IndexRequest(indexName);
                String s = objectMapper.writeValueAsString(calculateMetaData);
                indexRequest.source(s, XContentType.JSON);
                bulkRequest.add(indexRequest);
            }
            BulkResponse bulkResponse = EsUtils.REST_HIGH_LEVEL_CLIENT.bulk(bulkRequest, RequestOptions.DEFAULT);
            //是否失败 ,返回false 代表成功
            return !bulkResponse.hasFailures();
        }
        return true;
    }

    public static boolean addData(BulkRequest bulkRequest) throws Exception {
        BulkResponse bulkResponse = EsUtils.REST_HIGH_LEVEL_CLIENT.bulk(bulkRequest, RequestOptions.DEFAULT);
        //是否失败 ,返回false 代表成功
        return !bulkResponse.hasFailures();
    }

    /**
     * 通用查询方法(无size,默认10000)带排除字段
     * 复杂查询
     *
     * @param indexName
     * @param includes
     * @param excludes
     * @return
     * @throws Exception
     */
    public static Map<String, Object> search(String indexName,
                                             BoolQueryBuilder boolQueryBuilder,
                                             String[] includes,
                                             String[] excludes) throws Exception {
        SearchRequest searchRequest = new SearchRequest(indexName);
        //构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().trackTotalHits(true).size(10000);
        sourceBuilder.fetchSource(includes, excludes);
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.timeout(TimeValue.timeValueHours(1));
        searchRequest.source(sourceBuilder);
        return EsUtils.manageResult(searchRequest);
    }

    /**
     * 通用查询方法(无size,默认10000)带排除字段
     * 复杂查询(带聚合)
     *
     * @param indexName
     * @param includes
     * @param excludes
     * @return
     * @throws Exception
     */
    public static Map<String, Object> search(String indexName,
                                             BoolQueryBuilder boolQueryBuilder,
                                             AggregationBuilder aggregationBuilder,
                                             String[] includes,
                                             String[] excludes) throws Exception {
        SearchRequest searchRequest = new SearchRequest(indexName);
        //构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().trackTotalHits(true).size(10000);
        sourceBuilder.fetchSource(includes, excludes);
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.aggregation(aggregationBuilder);
        sourceBuilder.timeout(TimeValue.timeValueHours(1));
        searchRequest.source(sourceBuilder);
        return EsUtils.manageResult(searchRequest);
    }

    /**
     * 处理结果方法
     *
     * @param searchRequest 搜索请求
     * @return map
     * @throws IOException 操作异常
     */
    private static Map<String, Object> manageResult(SearchRequest searchRequest) throws IOException {
        List<Map> result = new ArrayList<>();
        SearchResponse searchResponse = EsUtils.REST_HIGH_LEVEL_CLIENT.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            result.add(sourceAsMap);
        }
        // 总数
        int total = (int) hits.getTotalHits().value;
        Map<String, Object> map = new HashMap<>();
        map.put("data", result);
        map.put("total", total);
        return map;
    }

    /**
     * 清除滚屏
     *
     * @param scrollId
     * @return
     */
    private static boolean clearScroll(String scrollId) throws IOException {
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = EsUtils.REST_HIGH_LEVEL_CLIENT.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        boolean succeeded = clearScrollResponse.isSucceeded();
        return succeeded;
    }

    /**
     * 多条件查询--深分页,一次返回匹配条件的全部数据
     *
     * @param boolQuery 构建多条件查询
     * @return
     * @throws IOException
     */
    public static List<Map> getEsData(String indexName, BoolQueryBuilder boolQuery) throws Exception {
        SearchRequest searchRequest = new SearchRequest(indexName);
        //构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().trackTotalHits(true).size(50000);
        Scroll scroll = new Scroll(TimeValue.timeValueHours(1L));
        sourceBuilder.query(boolQuery);
        sourceBuilder.timeout(TimeValue.timeValueHours(1));
        searchRequest.source(sourceBuilder);
        searchRequest.scroll(scroll);
        SearchResponse searchResponse = EsUtils.REST_HIGH_LEVEL_CLIENT.search(searchRequest, RequestOptions.DEFAULT);

        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        List<Map> result = new LinkedList<>();
        while (searchHits != null && searchHits.length > 0) {
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                result.add(hit.getSourceAsMap());
            }
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            searchResponse = EsUtils.REST_HIGH_LEVEL_CLIENT.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();
        }
        // 清除滚屏
        if (scrollId != null) {
            EsUtils.clearScroll(scrollId);
        }
        return result;
    }

}
