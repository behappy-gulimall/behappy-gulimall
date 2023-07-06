package org.xiaowu.behappy.common.core.enums;

/**
 * @author xiaowu
 * @Description: 错误状态码枚举
 *
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 *      002：短信验证码频率太高
 *  11: 商品
 *  12: 订单
 *  13: 购物车
 *  14: 物流
 *  15：用户
 **/

public enum BizCodeEnum {

    OK(0,"success"),
    UNKNOWN_EXCEPTION(10000,"系统未知异常"),
    VALID_EXCEPTION(10001,"参数格式校验失败"),
    TO_MANY_REQUEST(10002,"请求流量过大，请稍后再试"),
    SMS_CODE_EXCEPTION(10003,"验证码获取频率太高，请稍后再试"),
    READ_TIME_OUT_EXCEPTION(10004,"远程调用服务超时，请重新再试"),
    SMS_SEND_FAIL(10005,"短信发送失败，请重新再试"),
    HTTP_ERROR_EXCEPTION(10006,"请求错误，请重新再试"),
    ILLEGAL_CALLBACK_REQUEST_ERROR(10007,"非法回调请求，请重新再试"),
    MD5_ERROR(10008,"MD5处理出错"),
    PRODUCT_UP_EXCEPTION(11000,"商品上架异常"),
    NO_STOCK_EXCEPTION(11001,"商品库存不足"),
    MENU_HASH_PROP_EXCEPTION(11002,"该菜单下面还有属性，无法删除"),
    RES_ISNULL_FROM_ORDER_EXCEPTION(11003,"通过订单编号查询出来的结果为空"),
    ORDER_INVALID_EXCEPTION(11004,"订单无效"),
    WX_PAY_EXCEPTION(12000,"微信支付失败"),
    EMPTY_CART_EXCEPTION(13001,"购物车列表为空"),
    USER_EXIST_EXCEPTION(15001,"存在相同的用户"),
    PHONE_EXIST_EXCEPTION(15002,"存在相同的手机号"),
    LOGINACCT_PASSWORD_EXCEPTION(15003,"账号或密码错误");

    private Integer code;

    private String message;

    BizCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
