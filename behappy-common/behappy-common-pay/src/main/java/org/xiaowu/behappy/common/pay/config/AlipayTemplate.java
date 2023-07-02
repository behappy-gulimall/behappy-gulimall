package org.xiaowu.behappy.common.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 支付宝模板
 * @author xiaowu
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alipay")
public class AlipayTemplate {

    /**
     * 在支付宝创建的应用的id
     */
    private String appId = "2016102100732649";

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    private String merchantPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDDRCE37/KHJF/5+6YsDELw5LzrXHjdpoXWL37d1wOxD52a+gjCmfks3zsw3RlU+Ip+QUYqsvOH/8it+PYXTLDnPqplPXysybIAucc7QjTsjamlN1atANdlPtg+FokxWk7QdCXK02xj1MXf4+JK0gvCo++bG+mWyyzGJ/bbA4JjCvBW+iw7nWKb4e0vhdOtqfeLvJmYWc36zPRNo96e6YlbBHMJmDZ74+jgYl/bxkop5LU6FSSQ5jrfqzyxRLyO5sp0rFVoPTvL2kg1c0j8CZNuUGz1hbPVrXgUXXMAc3dZWu3hVsB0WWogRw+iJvxdzQRQtt7iotAXq96BR1GaSovzAgMBAAECggEAA91ITwQXp6cHoznguwRvGrdcKchFuBVAltGP0DIeJ5Xoh5WlMLy9Wq78S+ZERxM4InGGQvgRq8JbJV5E+IcTBTCPTLufyQeg1zTWBY004O2YyuVCeOAIYz+QJgJqKqjf4G48BTyT65HdbKXk55gqlWqLJmWyBML2muFVmFYEcZNP8KMKjG4ImpiB8TDuNmyzCeyef2k6j0yrn+wCkOFD3MGGJvq4DVqeGHowJdYLhvBCWg72Cz6zi3d+/samNpSTfzUBHWjJCOivw/B9uiqg0QPJCUvYAJFsXBs7ofav3soPkZ8B7FiinCnY7VU5CpZA1u75vnC+hE09SpnQln8m0QKBgQD6EQDOV02NUfXsubJhIZg0TJ5eYQltdCAG9njgcX1Hg9ZJyk4f/s05UnJvZl2n31SNoG8LkhSB//74giPzVi6oAP53koPjkYJQR10bk22tnoRXDOB7q/PFejliVcUweg30e67OAZzDeSlnfS+uNCA7eNIP6KMWYGLOmqRGbW6QiQKBgQDH5j/LWA7jkruJsqTB8dGgAs2x9hC5DC29zRnZp1rhSZaxor2ZTdWgE+zF+XyH1zFA3vus5uo7ECB4GXRQZBT7gUbKLzdclAZOUHPFdsOMrNaWOI4XNJMfw2ZgBf5Xu/7Hn6tSK3LZo8prbuITUc6l2Evg7x4TAMQcTzGSBmwKBgQDTf3AEFNi1rt1lo0VYW3aEvUywnDfCCBZSbUGc6r+/raSe8mkTLIlccvvwdk69/ehghJGG4r1PmjmG62MGmxyI4ZZXgWblIRtrVIBOI2n/DvP1QCHY130sx4wjPFG0B3coETeuarSwqxg2vC2Ik/Oy+SQJaoQNF+ZZwXcxTbrWqQKBgDrqBjU077ZsgW8UwnI/Q34ade7/ULP3OAlhE5K1hjU9v7bAArefavsOaCjHq4+WFBkwLRgw4XyzGe52gfrYJds165ELIvcHJHhYnZkSaqR1RI5wkilxGLccNv0p9B3E73hvzYwPbrK6V9xYXAGTwTooGHKfJp/frqVCDdbs71YvAoGBANxypr6ACemTBnWcKVhxQD0QZevqWMADlCyORaYILIwhb3s5uE7Rc2wvuJLBO8t/TxD37kYYwIZJXLH9vtj9ZkeY0KWucHygmTaoQ9Z1blaqlU082YvfvxJ2PTOUe5Vk+msO2ntFFkcZycklEQC6q93SUoxTl79gjehSFEABMot2";

    /**
     * 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
     */
    private String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkw7OmxubpJqkqhsdu3yPRVRnDJTjs1Cfl2lox10kv8sKkQwYwtojJQ45RgoSnn1vo/rda/F73pLoANYgE+RpNx8kghatwYDsqQm3S4B84aMR+7GMN9qMm6dXXxoKGn5aY+9NPjkCDO9YeZ4HPn1Yqndj7Y//YfHVSmQQPQKvuWRGGr1bVYUW/IpKBOGr77oSgea0sm42GxPoLypuTiVfq/7AcjmZ3wbTYF2NMwebFXUJmwR5EjdBQa/CnVDKDwpB4doqlP/HpXaepadHYWJZEPokt+nZyiW5l9HrsHU/67A/BSpC/MX/nrtonjnK+j0w/ywvUIIPbzDUpwuTClnNGQIDAQAB";

    /**
     * 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     * 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
     */
    private String notifyUrl = "http://member.glmall.com/memberOrder.html";

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     * 同步通知，支付成功，一般跳转到成功页
     */
    private String returnUrl = "http://member.glmall.com/memberOrder.html";

    /**
     * 签名方式
     */
    private String signType = "RSA2";

    /**
     * 字符编码格式
     */
    private String charset = "utf-8";

    /**
     * 自动关单时间
     */
    private String timeout = "15m";

    /**
     * 支付宝网关； https://openapi.alipaydev.com/gateway.do
     */
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}
