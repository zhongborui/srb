package com.arui.srb.core.controller.api;


import com.arui.common.exception.Assert;
import com.arui.common.exception.BusinessException;
import com.arui.common.result.R;
import com.arui.common.result.ResponseEnum;
import com.arui.common.util.RegexValidateUtils;
import com.arui.srb.core.pojo.vo.UserInfoVO;
import com.arui.srb.core.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Api(tags = "前台会员接口")
@RestController
@CrossOrigin
@RequestMapping("/api/core/userInfo")
public class ApiUserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation(value = "前台用户注册接口")
    @PostMapping("/register")
    public R register(
            @ApiParam(value = "用户注册信息", required = true)
            @RequestBody UserInfoVO userInfoVO){
        // 一系列的校验
        String mobile = userInfoVO.getMobile();
        String password = userInfoVO.getPassword();
        String registerCode = userInfoVO.getRegisterCode();

        // MOBILE_NULL_ERROR(-202, "手机号码不能为空"),
        Assert.notNull(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        // MOBILE_ERROR(-203, "手机号码不正确"),
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);
        // PASSWORD_NULL_ERROR(204, "密码不能为空"),
        Assert.notNull(password, ResponseEnum.PASSWORD_NULL_ERROR);
//        CODE_NULL_ERROR(205, "验证码不能为空"),
        Assert.notNull(registerCode, ResponseEnum.CODE_NULL_ERROR);

        userInfoService.register(userInfoVO);
        return R.ok();
    }
}

