package org.xiaowu.behappy.order.web;

import cn.hutool.core.util.StrUtil;
import com.alipay.api.AlipayApiException;
import com.github.wxpay.sdk.WXPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xiaowu.behappy.api.order.vo.PayVo;
import org.xiaowu.behappy.common.core.exception.GulimallException;
import org.xiaowu.behappy.common.core.utils.IpUtil;
import org.xiaowu.behappy.common.pay.client.WxHttpClient;
import org.xiaowu.behappy.common.pay.config.WxPayProperties;
import org.xiaowu.behappy.order.entity.OrderEntity;
import org.xiaowu.behappy.order.service.AliPayService;
import org.xiaowu.behappy.order.service.OrderService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static org.xiaowu.behappy.common.core.enums.BizCodeEnum.ORDER_INVALID_EXCEPTION;

/**
 * @author xiaowu
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class PayWebController {

    private final OrderService orderService;

    private final AliPayService aliPayService;

    private final WxPayProperties wxPayProperties;

    private final HttpServletRequest httpServletRequest;

    private final WxHttpClient wxHttpClient;

    /**
     * 用户下单:支付宝支付
     * 1、让支付页让浏览器展示
     * 2、支付成功以后，跳转到用户的订单列表页
     * @param orderSn
     * @return
     * @throws AlipayApiException
     */
    @ResponseBody
    @GetMapping(value = "/aliPayOrder",produces = "text/html")
    public String aliPayOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {

        PayVo payVo = orderService.getOrderPay(orderSn);
        return aliPayService.pay(payVo);
    }


    /**
     * 微信支付
     * @param orderSn
     * @return
     */
    @GetMapping(value = "/weixinPayOrder")
    public String weixinPayOrder(@RequestParam("orderSn") String orderSn, Model model) throws Exception {

        OrderEntity order = orderService.getOrderByOrderSn(orderSn);

        if (order == null) {
            throw new GulimallException(ORDER_INVALID_EXCEPTION);
        }
        //1、设置参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", wxPayProperties.getAppId());
        paramMap.put("mch_id", wxPayProperties.getMchId());
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        paramMap.put("body", StrUtil.nullToDefault(order.getNote(), "收货人：%s\n订单号：%s".formatted(order.getReceiverName(), orderSn)));
        paramMap.put("out_trade_no", order.getOrderSn());
        // 单位是分
        // paramMap.put("total_fee", "1");
        // 将金额乘以 100，并将结果转换为 BigInteger
        BigInteger cents = order.getPayAmount().multiply(new BigDecimal("100")).toBigInteger();
        paramMap.put("total_fee", cents.toString());

        paramMap.put("spbill_create_ip", IpUtil.getIpAddr(httpServletRequest));
        paramMap.put("notify_url", wxPayProperties.getNotifyUrl());
        paramMap.put("trade_type", "NATIVE");
        //2、微信统一支付 接口
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        //3、返回第三方的数据
        String xml = wxHttpClient.execute(url, paramMap);
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
        log.debug("微信支付码返回信息: {}", resultMap.get("return_msg"));
        //4、封装返回结果集. 传入前台的二维码路径生成支付二维码
        model.addAttribute("codeUrl",resultMap.get("code_url"));
        model.addAttribute("orderId",order.getOrderSn());
        model.addAttribute("returnUrl",wxPayProperties.getReturnUrl());

        return "createForWxNative";
    }

    /**
     * 根据订单号查询订单状态的API
     * @param orderId
     * @return
     */
    @GetMapping(value = "/queryByOrderId")
    @ResponseBody
    public OrderEntity queryByOrderId(@RequestParam("orderId") String orderId) {
        log.debug("查询支付记录...");
        return orderService.getOrderByOrderSn(orderId);
    }

}
