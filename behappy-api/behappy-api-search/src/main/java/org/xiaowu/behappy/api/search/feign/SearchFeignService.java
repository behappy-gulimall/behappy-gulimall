package org.xiaowu.behappy.api.search.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.xiaowu.behappy.api.common.vo.SkuEsModel;
import org.xiaowu.behappy.api.search.feign.factory.SearchFeignFactory;
import org.xiaowu.behappy.common.core.result.R;

import java.util.List;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.SEARCH_SERVICE;

/**
 * @author xiaowu
 */
@FeignClient(value = SEARCH_SERVICE,
        path = "/search",
        fallbackFactory = SearchFeignFactory.class)
public interface SearchFeignService {

    /**
     * 上架
     * @param skuEsModels
     * @return
     */
    @PostMapping(value = "/save/product")
    R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}
