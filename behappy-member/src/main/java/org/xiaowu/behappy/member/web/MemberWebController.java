package org.xiaowu.behappy.member.web;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiaowu.behappy.api.order.feign.feign.OrderFeignService;
import org.xiaowu.behappy.common.core.result.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaowu
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberWebController {

    private final OrderFeignService orderFeignService;

    @GetMapping(value = "/memberOrder.html")
    public String memberOrderPage(@RequestParam(value = "pageNum",required = false,defaultValue = "0") Integer pageNum,
                                  Model model, HttpServletRequest request) {
        //获取到支付宝给我们转来的所有请求数据
        //request,验证签名

        //查出当前登录用户的所有订单列表数据
        Map<String,Object> page = new HashMap<>();
        page.put("page",pageNum.toString());

        //远程查询订单服务订单数据
        R orderInfo = orderFeignService.listWithItem(page);
        log.debug("MemberWebController - memberOrderPage: {}", JSON.toJSONString(orderInfo));
        model.addAttribute("orders",orderInfo);
        return "orderList";
    }

}
