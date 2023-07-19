package org.xiaowu.behappy.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.api.product.vo.Catelog2Vo;
import org.xiaowu.behappy.product.entity.CategoryEntity;
import org.xiaowu.behappy.product.service.CategoryService;

import java.util.List;
import java.util.Map;

/**
 * @author xiaowu
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class IndexController {

    private final CategoryService categoryService;

    /**
     * index/json/catalog.json
     * @return
     */
    @GetMapping(value = "/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        return categoryService.getCatalogJson();
    }

}
