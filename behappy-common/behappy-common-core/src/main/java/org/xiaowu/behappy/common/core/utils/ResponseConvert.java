package org.xiaowu.behappy.common.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.common.core.enums.BizCodeEnum;
import org.xiaowu.behappy.common.core.exception.GulimallException;
import org.xiaowu.behappy.common.core.result.R;

/**
 * @author 小五
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseConvert {

    private final ObjectMapper objectMapper;

    private static void assertResponse(R r) {
        try {
            assert r.getCode() == 0;
        } catch (Exception e) {
            log.error("assert response error -> code:{},msg:{}", r.getCode(), r.getMsg());
            throw new GulimallException(r.getCode(), r.getMsg());
        }
    }

    public <T> T convert(R r, TypeReference<T> type) {
        try {
            ResponseConvert.assertResponse(r);
            return objectMapper.readValue(objectMapper.writeValueAsString(r.getData()), type);
        } catch (JsonProcessingException e) {
            log.error("Response转换：序列化错误");
            throw new GulimallException(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), e.getMessage());
        } catch (GulimallException e) {
            throw e;
        } catch (Exception e) {
            log.error("Response转换：未知错误");
            throw e;
        }
    }
}
