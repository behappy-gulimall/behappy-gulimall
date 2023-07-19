package org.xiaowu.behappy.common.core.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.behappy.common.core.enums.BizCodeEnum;

/**
 * 自定义全局异常类
 * @author xiaowu
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "自定义全局异常类")
public class GulimallException extends RuntimeException {

    @Schema(description = "异常状态码")
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public GulimallException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     * @param bizCodeEnum
     */
    public GulimallException(BizCodeEnum bizCodeEnum) {
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "GulimallException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
