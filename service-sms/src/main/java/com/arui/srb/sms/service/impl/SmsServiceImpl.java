package com.arui.srb.sms.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.arui.common.exception.Assert;
import com.arui.common.exception.BusinessException;
import com.arui.common.result.ResponseEnum;
import com.arui.common.util.RandomUtils;
import com.arui.srb.sms.service.SmsService;
import com.arui.srb.sms.utils.SmsProperties;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ...
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void send(String mobile) {
        // 创建远程连接客户端对象
        DefaultProfile profile = DefaultProfile.getProfile(
                SmsProperties.REGION_Id, SmsProperties.KEY_ID, SmsProperties.KEY_SECRET
        );
        DefaultAcsClient acsClient = new DefaultAcsClient(profile);

        // (创建请求参数）发送请求
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", SmsProperties.REGION_Id);
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", SmsProperties.SIGN_NAME);
        request.putQueryParameter("TemplateCode", SmsProperties.TEMPLATE_CODE);

        // 生成随机验证码
        String fourBitRandom = RandomUtils.getFourBitRandom();
        Map<String, String> map = new HashMap();
        map.put("code", fourBitRandom);
        Gson gson = new Gson();
        String param = gson.toJson(map);
        request.putQueryParameter("TemplateParam", param);



        try {
            // 使用客户端对象发送请求，并得到响应结果
            CommonResponse response = acsClient.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            Assert.isTrue(success, ResponseEnum.ALIYUN_SMS_ERROR);

            // 调用redis存验证码
            redisTemplate.opsForValue().set("srb:sms:code:" + mobile, fourBitRandom, 5, TimeUnit.MINUTES);

        } catch (ClientException e) {
            throw new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR);
        }

        // 处理结果
    }
}
