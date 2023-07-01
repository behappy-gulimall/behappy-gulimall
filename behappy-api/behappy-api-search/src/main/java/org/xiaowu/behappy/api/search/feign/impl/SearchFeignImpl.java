package org.xiaowu.behappy.api.search.feign.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.xiaowu.behappy.api.common.vo.SkuEsModel;
import org.xiaowu.behappy.api.search.feign.SearchFeignService;
import org.xiaowu.behappy.common.core.result.R;

import java.util.List;

/**
 * @author xiaowu
 */
@Slf4j
public class SearchFeignImpl implements SearchFeignService {

    @Setter
    Throwable cause;

    @Override
    public R productStatusUp(List<SkuEsModel> skuEsModels) {
        log.error("SearchFeignFallBack - productStatusUp: {}", skuEsModels);
        return R.error(cause);
    }
}
