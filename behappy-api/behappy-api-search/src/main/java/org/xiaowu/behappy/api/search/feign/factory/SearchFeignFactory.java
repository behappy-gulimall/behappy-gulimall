package org.xiaowu.behappy.api.search.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.search.feign.SearchFeignService;
import org.xiaowu.behappy.api.search.feign.impl.SearchFeignImpl;

/**
 * @author xiaowu
 */
@Component
public class SearchFeignFactory implements FallbackFactory<SearchFeignService> {

    @Override
    public SearchFeignService create(Throwable cause) {
        SearchFeignImpl searchFeignImpl = new SearchFeignImpl();
        searchFeignImpl.setCause(cause);
        return searchFeignImpl;
    }
}
