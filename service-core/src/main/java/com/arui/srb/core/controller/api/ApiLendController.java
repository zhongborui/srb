package com.arui.srb.core.controller.api;


import com.arui.common.result.R;
import com.arui.srb.core.pojo.vo.LendVO;
import com.arui.srb.core.service.LendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的准备表 前端控制器
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Api(tags = "前台标的")
@RestController
@RequestMapping("/api/core/lend")
public class ApiLendController {

    @Resource
    private LendService lendService;

    @ApiOperation(value = "前台获取标的列表")
    @GetMapping("/list")
    public R list(){
        List<LendVO> lendList = lendService.getLendList();
        return R.ok().data("lendList", lendList);
    }

    @ApiOperation(value = "展示投资页面标详情")
    @GetMapping("/show/{lendId}")
    public R show(
            @ApiParam(value = "标的id", required = true)
            @PathVariable Long lendId
    ){
        Map<String, Object> lendDetail = lendService.getLendDetail(lendId);
        return R.ok().data("lendDetail", lendDetail);
    }


    @ApiOperation("计算投资收益")
    @GetMapping("/getInterestCount/{invest}/{yearRate}/{totalmonth}/{returnMethod}")
    public R getInterestCount(
            @ApiParam(value = "投资金额", required = true)
            @PathVariable("invest") BigDecimal invest,

            @ApiParam(value = "年化收益", required = true)
            @PathVariable("yearRate")BigDecimal yearRate,

            @ApiParam(value = "期数", required = true)
            @PathVariable("totalmonth")Integer totalmonth,

            @ApiParam(value = "还款方式", required = true)
            @PathVariable("returnMethod")Integer returnMethod) {

        BigDecimal interestCount = lendService.getInterestCount(invest, yearRate, totalmonth, returnMethod);
        return R.ok().data("interestCount", interestCount);
    }
}

