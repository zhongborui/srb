package com.arui.srb.core.controller.api;


import com.arui.common.exception.Assert;
import com.arui.common.result.R;
import com.arui.common.result.ResponseEnum;
import com.arui.common.util.RegexValidateUtils;
import com.arui.srb.base.util.JwtUtils;
import com.arui.srb.core.pojo.vo.LoginVO;
import com.arui.srb.core.pojo.vo.RegisterVO;
import com.arui.srb.core.pojo.vo.UserInfoVO;
import com.arui.srb.core.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
            @RequestBody RegisterVO registerVO){
        // 一系列的校验
        String mobile = registerVO.getMobile();
        String password = registerVO.getPassword();
        String registerCode = registerVO.getCode();

        // MOBILE_NULL_ERROR(-202, "手机号码不能为空"),
        Assert.notNull(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        // MOBILE_ERROR(-203, "手机号码不正确"),
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);
        // PASSWORD_NULL_ERROR(204, "密码不能为空"),
        Assert.notNull(password, ResponseEnum.PASSWORD_NULL_ERROR);
//        CODE_NULL_ERROR(205, "验证码不能为空"),
        Assert.notNull(registerCode, ResponseEnum.CODE_NULL_ERROR);

        userInfoService.register(registerVO);
        return R.ok();
    }

    @ApiOperation(value = "前台用户登录接口")
    @PostMapping("/login")
    public R login(
            @ApiParam(value = "用户登录对象VO")
            @RequestBody LoginVO loginVO,
            HttpServletRequest request){

        // 一系列判断
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);

        // 获取用户ip
//        String ip = request.getRemoteAddr();
        // nginx 转发ip
        String ip = request.getHeader("X-Forwarded-For");
        UserInfoVO userInfoVO = userInfoService.login(loginVO, ip);
        return R.ok().data("userInfo", userInfoVO);
    }

    @ApiOperation(value = "校验令牌接口")
    @GetMapping("/checkToken")
    public R checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        boolean result = JwtUtils.checkToken(token);
        if (!result){
            return R.setResult(ResponseEnum.LOGIN_AUTH_ERROR);
        }
        return R.ok();
    }

    @ApiOperation(value = "检查手机号是否注册")
    @GetMapping("/checkMobile/{mobile}")
    public boolean checkMobile(
            @ApiParam(value = "手机号", required = true)
            @PathVariable String mobile){
        return userInfoService.checkMobile(mobile);
    }
}

