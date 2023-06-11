package org.xiaowu.behappy.search.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.xiaowu.behappy.api.search.vo.SearchParam;
import org.xiaowu.behappy.api.search.vo.SearchResult;
import org.xiaowu.behappy.search.service.MallSearchService;


/**
 * @author xiaowu
 */
@Controller
@RequiredArgsConstructor
public class SearchController {

    private final MallSearchService mallSearchService;

    /**
     * 自动将页面提交过来的所有请求参数封装成我们指定的对象
     * @param param
     * @return
     */
    @GetMapping(value = "/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request) {
        param.set_queryString(request.getQueryString());
        //1、根据传递来的页面的查询参数，去es中检索商品
        SearchResult result = mallSearchService.search(param);
        model.addAttribute("result",result);
        return "list";
    }
}
