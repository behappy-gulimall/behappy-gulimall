package org.xiaowu.behappy.common.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xiaowu.behappy.common.core.result.R;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author 小五
 */
@Slf4j
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@RestControllerAdvice(basePackages = "org.xiaowu.behappy")
public class ServletExceptionHandler {

    @ExceptionHandler(GulimallException.class)
    public R beHappyExceptionHandler(GulimallException ex) {
        extractedErrPrint(ex);
        return R.error(ex.getCode(), ex.getMessage());
    }


    /**
     * 参数校验不通过异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        extractedErrPrint(ex);
        StringJoiner sj = new StringJoiner(";");
        ex.getBindingResult().getFieldErrors().forEach(x -> sj.add(x.getDefaultMessage()));
        return R.error(ex);
    }

    /**
     * Controller参数绑定错误
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        extractedErrPrint(ex);
        return R.error(ex);
    }


    /**
     * 其他错误
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception ex) {
        extractedErrPrint(ex);
        return R.error(ex);
    }

    private static void extractedErrPrint(Exception ex) {
        if (Objects.nonNull(ex.getCause())) {
            log.error("全局异常捕获", ex.getCause());
        }
        if (ex.getStackTrace().length>0) {
            StackTraceElement stackTraceElement = ex.getStackTrace()[0];
            log.error(
                    new StringJoiner(" - ")
                            .add(stackTraceElement.getClassName())
                            .add(stackTraceElement.getMethodName())
                            .add(String.valueOf(stackTraceElement.getLineNumber()))
                            .add(ex.getMessage()).toString());
        }else {
            log.error(ex.getMessage());
        }
    }
}
