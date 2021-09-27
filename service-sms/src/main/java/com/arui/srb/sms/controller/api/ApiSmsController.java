package com.arui.srb.sms.controller.api;

import com.arui.common.exception.Assert;
import com.arui.common.result.R;
import com.arui.common.result.ResponseEnum;
import com.arui.common.util.RegexValidateUtils;
import com.arui.srb.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ...
 */
@RestController
@Api(tags = "验证码短信接口")
@RequestMapping("/api/sms")
@CrossOrigin
@Slf4j
public class ApiSmsController {
    @Resource
    private SmsService smsService;

    @ApiOperation("获取验证码")
    @GetMapping("/send/{mobile}")
    public R send(
            @ApiParam(value = "手机号" , required = true)
            @PathVariable("mobile") String mobile
    ){
        //MOBILE_NULL_ERROR(-202, "手机号不能为空"),
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);

        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);

        smsService.send(mobile);

        return R.ok().message("短信发送成功");
    }
}