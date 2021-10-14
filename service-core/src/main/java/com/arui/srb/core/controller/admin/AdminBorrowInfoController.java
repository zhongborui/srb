package com.arui.srb.core.controller.admin;


import com.arui.common.result.R;
import com.arui.srb.core.pojo.vo.BorrowInfoApprovalVO;
import com.arui.srb.core.pojo.vo.BorrowInfoVO;
import com.arui.srb.core.service.BorrowInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 借款信息表 前端控制器
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Api(tags = "管理借款人借款接口")
@RestController
@RequestMapping("/admin/core/borrowInfo")
public class AdminBorrowInfoController {

    @Resource
    private BorrowInfoService borrowInfoService;

    @ApiOperation(value = "获得借款人列表接口")
    @GetMapping("/list")
    public R list(){
        List<BorrowInfoVO> list = borrowInfoService.getUserInfoList();
        return R.ok().data("list", list);
    }

    @ApiOperation(value = "查看借款人借款申请信息")
    @GetMapping("/show/{id}")
    public R show(
            @ApiParam(value = "用户id", required = true)
            @PathVariable Long id
    ){

        Map<String, Object> borrowInfoDetail =  borrowInfoService.show(id);
        return R.ok().data("borrowInfoDetail", borrowInfoDetail);
    }

    @ApiOperation(value = "审核借款人借款")
    @PostMapping("/approval")
    public R approval(
            @ApiParam(value = "审核借款申请对象", required = true)
            @RequestBody BorrowInfoApprovalVO borrowInfoApprovalVO
            ){
        borrowInfoService.approval(borrowInfoApprovalVO);
        return R.ok();
    }
}

