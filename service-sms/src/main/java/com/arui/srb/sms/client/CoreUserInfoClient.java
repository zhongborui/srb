package com.arui.srb.sms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ...
 */
@FeignClient(value = "service-core")
public interface CoreUserInfoClient {

    /**
     * 调用service-core的checkMobile方法，检查是否手机号已经注册了
     * @param mobile
     * @return
     */
    @GetMapping("/api/core/userInfo/checkMobile/{mobile}")
    public boolean checkMobile(@PathVariable String mobile);
}
