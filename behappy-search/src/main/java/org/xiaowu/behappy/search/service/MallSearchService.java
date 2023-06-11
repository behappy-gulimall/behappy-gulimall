package org.xiaowu.behappy.search.service;

import org.xiaowu.behappy.api.search.vo.SearchParam;

import javax.naming.directory.SearchResult;

public interface MallSearchService {

    /**
     * @param param 检索的所有参数
     * @return  返回检索的结果，里面包含页面需要的所有信息
     */
    SearchResult search(SearchParam param);
}
