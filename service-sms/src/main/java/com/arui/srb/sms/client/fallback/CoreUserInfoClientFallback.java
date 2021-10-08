package com.arui.srb.sms.client.fallback;

import com.arui.srb.sms.client.CoreUserInfoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ...
 */
@Service
@Slf4j
public class CoreUserInfoClientFallback implements CoreUserInfoClient {

    /**
     * 无法检查是否手机号注册时，
     * 直接发短信
     * @param mobile
     * @return
     */
    @Override
    public boolean checkMobile(String mobile) {
        log.info("远程调用失败，服务熔断了。。。。。");
        log.error("error 服务熔断");
        return false;
    }
}
