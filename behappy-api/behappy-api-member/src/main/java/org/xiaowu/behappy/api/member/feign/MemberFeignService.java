package org.xiaowu.behappy.api.member.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xiaowu.behappy.api.member.feign.factory.MemberFeignFactory;
import org.xiaowu.behappy.common.core.result.R;

import static org.xiaowu.behappy.common.core.constants.ServiceConstants.MEMBER_SERVICE;

/**
 * @author xiaowu
 */
@FeignClient(value = MEMBER_SERVICE,
        path = "/member",
        fallbackFactory = MemberFeignFactory.class)
public interface MemberFeignService {

    /**
     * 根据id获取用户地址信息
     * @param id
     * @return
     */
    @RequestMapping("/memberreceiveaddress/info/{id}")
    R info(@PathVariable("id") Long id);

}
