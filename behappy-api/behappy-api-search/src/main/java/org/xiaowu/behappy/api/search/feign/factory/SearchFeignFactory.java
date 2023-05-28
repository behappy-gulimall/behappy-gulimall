package org.xiaowu.behappy.api.search.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.search.feign.SearchFeignService;
import org.xiaowu.behappy.api.search.feign.fallback.SearchFeignFallBack;

/**
 * @author xiaowu
 */
@Component
public class SearchFeignFactory implements FallbackFactory<SearchFeignService> {

    @Override
    public SearchFeignService create(Throwable cause) {
        SearchFeignFallBack searchFeignFallBack = new SearchFeignFallBack();
        searchFeignFallBack.setCause(cause);
        return searchFeignFallBack;
    }
}
