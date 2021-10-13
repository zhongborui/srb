package com.arui.srb.core.controller.api;


import com.arui.common.result.R;
import com.arui.srb.base.util.JwtUtils;
import com.arui.srb.core.pojo.entity.BorrowInfo;
import com.arui.srb.core.service.BorrowInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * <p>
 * 借款信息表 前端控制器
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Api(tags = "前台借款信息接口")
@RestController
@RequestMapping("/api/core/borrowInfo")
public class ApiBorrowInfoController {

    @Resource
    private BorrowInfoService borrowInfoService;

    @ApiOperation(value = "获得借款金额接口")
    @GetMapping("/auth/getBorrowAmount")
    public R getBorrowAmount(
            @ApiParam(value = "请求")
            HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        BigDecimal borrowAmount = borrowInfoService.getBorrowAmount(userId);
        return R.ok().data("borrowAmount", borrowAmount);
    }

    @ApiOperation(value = "保存借款申请信息")
    @PostMapping("/auth/save")
    public R save(
            @ApiParam(value = "借款人借款申请表单")
            @RequestBody BorrowInfo borrowInfo,
            HttpServletRequest request
            ){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        borrowInfo.setUserId(userId);
        borrowInfoService.saveBorrowInfo(borrowInfo);
        return R.ok();
    }

    @ApiOperation(value = "获得借款人借款申请状态")
    @GetMapping("/auth/getBorrowInfoStatus")
    public R getBorrowInfoStatus(HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer borrowInfoStatus = borrowInfoService.getBorrowInfoStatus(userId);
        return R.ok().data("borrowInfoStatus", borrowInfoStatus);
    }

}

