package org.xiaowu.behappy.search.service;

import org.xiaowu.behappy.api.common.vo.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ProductSaveService {

    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
