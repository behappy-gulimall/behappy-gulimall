package org.xiaowu.behappy.product.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.xiaowu.behappy.product.entity.CategoryEntity;
import org.xiaowu.behappy.product.service.CategoryService;

import java.util.List;

/**
 * @author xiaowu
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexWebController {

    private final CategoryService categoryService;

    @GetMapping(value = {"/","index.html"})
    private String indexPage(Model model) {
        //1、查出所有的一级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categories",categoryEntities);
        return "index";
    }

}
