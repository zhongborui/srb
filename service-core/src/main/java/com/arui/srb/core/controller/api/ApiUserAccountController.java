package com.arui.srb.core.controller.api;


import com.arui.common.result.R;
import com.arui.srb.base.util.JwtUtils;
import com.arui.srb.core.hfb.RequestHelper;
import com.arui.srb.core.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 用户账户 前端控制器
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Api(tags = "前台会员账户接口")
@RestController
@RequestMapping("/api/core/userAccount")
public class ApiUserAccountController {

    @Resource
    private UserAccountService userAccountService;

    @ApiOperation(value = "投资人充值接口")
    @PostMapping("/auth/commitCharge/{chargeAmt}")
    public R charge(
            @ApiParam(value = "充值金额", required = true)
            @PathVariable BigDecimal chargeAmt,

            HttpServletRequest request
            ){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        String formStr = userAccountService.charge(chargeAmt, userId);
        return R.ok().data("formStr", formStr);
    }

    @ApiOperation(value = "notify回调接口")
    @PostMapping("/notify")
    public String chargeNotify(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = RequestHelper.switchMap(parameterMap);
        // 校验第三方平台签名
        boolean b = RequestHelper.isSignEquals(paramMap);
        if (!b){
            return "false";
        }
        userAccountService.chargeNotify(paramMap);
        return "success";
    }

    @ApiOperation("查询账户余额")
    @GetMapping("/auth/getAccount")
    public R getAccount(HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        BigDecimal account = userAccountService.getAccount(userId);
        return R.ok().data("account", account);
    }
}

