package org.xiaowu.behappy.search.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.api.search.vo.SearchParam;
import org.xiaowu.behappy.search.service.MallSearchService;

import javax.naming.directory.SearchResult;

@Slf4j
@Service
public class MallSearchServiceImpl implements MallSearchService {

    @Override
    public SearchResult search(SearchParam param) {
        return null;
    }
}
