package org.xiaowu.behappy.api.thirdparty.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiaowu.behappy.api.thirdparty.feign.factory.ThirdPartFeignFactory;
import org.xiaowu.behappy.common.core.result.R;

import static org.xiaowu.behappy.common.core.constants.ServiceConstants.PRODUCT_SERVICE;
import static org.xiaowu.behappy.common.core.constants.ServiceConstants.THIRD_PARTY_SERVICE;

/**
 * @author xiaowu
 */
@FeignClient(value = THIRD_PARTY_SERVICE,
        fallbackFactory = ThirdPartFeignFactory.class)
public interface ThirdPartFeignService {
    @GetMapping("/sms/sendcode")
    R sendCode(@RequestParam("phone")String phone, @RequestParam("code")String code);
}
