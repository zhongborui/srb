package com.arui.srb.core.controller.api;


import com.arui.common.result.R;
import com.arui.srb.core.pojo.vo.LendVO;
import com.arui.srb.core.service.LendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
}

