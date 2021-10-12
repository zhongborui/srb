package com.arui.srb.core.controller.api;


import com.arui.common.result.R;
import com.arui.srb.base.util.JwtUtils;
import com.arui.srb.core.pojo.entity.UserBind;
import com.arui.srb.core.pojo.vo.BorrowerVO;
import com.arui.srb.core.service.BorrowerService;
import com.arui.srb.core.service.UserBindService;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 借款人 前端控制器
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Api(tags = "前台借款人接口")
@RestController
@RequestMapping("/api/core/borrower")
public class ApiBorrowerController {

    @Resource
    private BorrowerService borrowerService;

    @ApiOperation(value = "保存借款人额度信息接口")
    @PostMapping("/save")
    public R save(
            @ApiParam(value = "借款人信息表单", required = true)
            @RequestBody BorrowerVO borrowerVO,
            HttpServletRequest request
            ){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        borrowerService.saveBorrower(borrowerVO, userId);
        return R.ok().message("信息提交成功");
    }

    @GetMapping("/auth/getBorrowerStatus")
    public R getBorrowerStatus(HttpServletRequest request){
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        Integer borrowerStatus = borrowerService.getBorrowerStatusByUserId(userId);
        return R.ok().data("borrowerStatus", borrowerStatus);
    }
}

