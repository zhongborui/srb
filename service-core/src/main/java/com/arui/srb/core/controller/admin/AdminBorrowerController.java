package com.arui.srb.core.controller.admin;


import com.arui.common.result.R;
import com.arui.srb.core.pojo.entity.Borrower;
import com.arui.srb.core.pojo.vo.ApprovalFormVO;
import com.arui.srb.core.pojo.vo.BorrowerDetailVO;
import com.arui.srb.core.pojo.vo.BorrowerVO;
import com.arui.srb.core.service.BorrowerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 借款人 前端控制器
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Api(tags = "后端借款人管理")
@RestController
@RequestMapping("/admin/core/borrower")
public class AdminBorrowerController {

    @Resource
    private BorrowerService borrowerService;

    @ApiOperation(value = "获得借款人分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R getPageList(
            @ApiParam(value = "当前页", required = true)
            @PathVariable Integer page,

            @ApiParam(value = "每页显示多少条", required = true)
            @PathVariable Integer limit,

            @ApiParam(value = "查询关键字")
            @RequestParam String keyword){
        Page<Borrower> borrowerPage = new Page<>(page, limit);
        IPage<Borrower> pageModel = borrowerService.listPage(borrowerPage, keyword);
        return R.ok().data("pageModel", pageModel);
    }

    @ApiOperation(value = "展示借款人信息详情")
    @GetMapping("/show/{id}")
    public R showBorrowDetail(
            @ApiParam(value = "借款人id", required = true)
            @PathVariable Long id
    ){
        BorrowerDetailVO borrowerDetailVO = borrowerService.showBorrowDetailByUserId(id);
        return R.ok().data("borrowerDetailVO", borrowerDetailVO);
    }

    @ApiOperation(value = "审核借款人提交信息")
    @PostMapping("/approval")
    public R approval(
            @ApiParam(value = "积分审核表单", required = true)
            @RequestBody ApprovalFormVO approvalFormVO
            ){
        borrowerService.approval(approvalFormVO);
        return R.ok();
    }
}

