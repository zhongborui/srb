package com.arui.srb.core.controller.api;


import com.arui.common.result.R;
import com.arui.srb.base.util.JwtUtils;
import com.arui.srb.core.hfb.RequestHelper;
import com.arui.srb.core.pojo.vo.UserBindVO;
import com.arui.srb.core.service.UserBindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 用户绑定表 前端控制器
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Api(tags = "前台用户账户绑定接口")
@RestController
@RequestMapping("/api/core/userBind")
public class ApiUserBindController {

    @Resource
    private UserBindService userBindService;

    @ApiOperation(value = "srb平台调用hfb接口完成账户绑定")
    @PostMapping("/auth/bind")
    public R bind(
            @ApiParam(value = "用户绑定账户对象", required = true)
            @RequestBody UserBindVO userBindVO,
            HttpServletRequest request
            ){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        String formStr = userBindService.commitUserBind(userBindVO, userId);
        return R.ok().data("formStr", formStr);
    }

    @ApiOperation(value = "srb调用hfb接口，异步处理结果接口")
    @PostMapping("/notify")
    public String bindNotify(HttpServletRequest request){
        Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());
        // 检验签名
        if (!RequestHelper.isSignEquals(paramMap)){
            return "false";
        }
        // 接口幂等性
        boolean isBind = userBindService.isBind(paramMap);
        if (!isBind){
            return "false";
        }
        userBindService.bindNotify(paramMap);
        return "success";
    }
}

