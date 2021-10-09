package com.arui.srb.core.controller.admin;

import com.arui.common.result.R;
import com.arui.srb.core.pojo.entity.UserLoginRecord;
import com.arui.srb.core.service.UserLoginRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ...
 */
@Api(tags = "用户登录日志接口")
@RestController
@RequestMapping("/admin/core/userLoginRecord")

public class AdminUserLoginRecordController {

    @Resource
    private UserLoginRecordService userLoginRecordService;

    @ApiOperation("获取会员登录日志列表")
    @GetMapping("/listTop50/{userId}")
    public R listTop50(
            @ApiParam(value = "用户id")
            @PathVariable Long userId){

        List<UserLoginRecord> userLoginRecordList =  userLoginRecordService.listTop50(userId);
        return R.ok().data("list", userLoginRecordList);
    }

}
