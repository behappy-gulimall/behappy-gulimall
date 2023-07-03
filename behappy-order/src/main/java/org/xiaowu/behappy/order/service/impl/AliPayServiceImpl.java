package org.xiaowu.behappy.order.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.api.order.vo.PayVo;
import org.xiaowu.behappy.common.pay.config.AlipayTemplate;
import org.xiaowu.behappy.order.service.AliPayService;

/**
 *
 * @author xiaowu
 */
@Slf4j
@Service
@AllArgsConstructor
public class AliPayServiceImpl implements AliPayService {

    private final AlipayTemplate alipayTemplate;

    @SneakyThrows
    public String pay(PayVo vo) {
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(
                alipayTemplate.getGatewayUrl(),
                alipayTemplate.getAppId(),
                alipayTemplate.getMerchantPrivateKey(),
                "json",
                alipayTemplate.getCharset(),
                alipayTemplate.getAlipayPublicKey(),
                alipayTemplate.getSignType());

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayTemplate.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayTemplate.getNotifyUrl());

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String outTradeNo = vo.getOut_trade_no();
        //付款金额，必填
        String totalAmount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();
        // 30分钟内不付款就会自动关单
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\","
                + "\"total_amount\":\"" + totalAmount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + alipayTemplate.getTimeout() + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();
        log.debug("AliPayServiceImpl - pay: {}", result);
        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        return result;
    }
}
