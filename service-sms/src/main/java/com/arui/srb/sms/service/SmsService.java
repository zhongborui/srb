package com.arui.srb.sms.service;

/**
 * @author ...
 */
public interface SmsService {
    /**
     * 发送短信接口
     * @param mobile 手机号
     */
    void send(String mobile);
}
