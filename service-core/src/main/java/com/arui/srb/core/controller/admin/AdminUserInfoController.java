package com.arui.srb.core.controller.admin;


import com.arui.common.result.R;
import com.arui.srb.core.pojo.entity.UserInfo;
import com.arui.srb.core.pojo.query.UserInfoQuery;
import com.arui.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Api(tags = "后台会员管理接口")

@RestController
@RequestMapping("/admin/core/userInfo")
public class AdminUserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation(value = "后台查询会员列表接口")
    @GetMapping("/list/{page}/{limit}")
    public R list(
            @ApiParam(value = "当前页", example = "1")
            @PathVariable(value = "page") Integer page,

            @ApiParam(value = "每页的数据条数", example = "5")
            @PathVariable(value = "limit") Integer limit,

            @ApiParam(value = "会员列表查询对象")
            UserInfoQuery userInfoQuery){

        // 分页对象
        Page<UserInfo> userInfoPage = new Page<>(page, limit);

        Page<UserInfo> userInfoList = userInfoService.listPage(userInfoPage, userInfoQuery);
        return R.ok().data("pageModel", userInfoList);
    }

    @ApiOperation(value = "锁定用户接口")
    @PutMapping("/lock/{id}/{status}")
    public R lock(
            @ApiParam(value = "用户id", required = true)
            @PathVariable Long id,
            @ApiParam(value = "是否锁定,0锁定、1正常", required = true)
            @PathVariable Integer status){

        userInfoService.lock(id, status);
        return R.ok().message((status == 1)? "正常" : "锁定");
    }
}

