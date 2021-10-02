package com.arui.srb.core.controller.admin;


import com.arui.common.exception.Assert;
import com.arui.common.exception.BusinessException;
import com.arui.common.result.R;
import com.arui.common.result.ResponseEnum;
import com.arui.srb.core.pojo.entity.IntegralGrade;
import com.arui.srb.core.service.IntegralGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台控制器
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Slf4j
@Api(tags = "后台积分等级管理接口")
@CrossOrigin
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {
    @Resource
    private IntegralGradeService integralGradeService;

    /**
     *  查询积分的等级列表
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "积分等级列表")
    public R list(){
        log.info("arui info");
        log.warn("arui warning");
        log.error("arui error");
        List<IntegralGrade> list = integralGradeService.list();
        return R.ok().data("list", list);
    }

    /**
     *  根据id删除积分等级
     * @param id
     * @return
     */
    @DeleteMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除积分等级", notes = "逻辑删除")
    public R removeById(
            @ApiParam(value = "数据id", required = true, example = "10")
            @PathVariable("id") Long id){
        boolean b = integralGradeService.removeById(id);
        if (b){
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");
    }

    /**
     * 新增积分等级
     * @param integralGrade 传入积分等级表单
     * @RequestBody 表单数据传递必须写@RequestBody， 否则以普通参数传入
     * @return
     */
    @ApiOperation(value = "新增积分等级")
    @PostMapping("/save")
    public R save(
            @ApiParam(value = "积分等级表单", required = true)
            @RequestBody IntegralGrade integralGrade
    ){
//        if (integralGrade.getBorrowAmount() == null){
//            // 传入一个枚举类给BusinessException构造器， 创建一个异常对象e
//            throw new BusinessException(ResponseEnum.BORROW_AMOUNT_NULL_ERROR);
//        }
        // 是断言替代if else （类似策略模式的优雅代码形式）
        Assert.notNull(integralGrade.getBorrowAmount(), ResponseEnum.BORROW_AMOUNT_NULL_ERROR);

        boolean result = integralGradeService.save(integralGrade);
        if (result){
            return R.ok().message("保存成功");
        }
        return R.error().message("保存失败");
    }

    /**
     *
     * @param id 根据id查询积分等级
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id查询积分等级")
    public R get(
            @ApiParam(value = "数据id", required = true, example = "1")
            @PathVariable("id") Long id){
        IntegralGrade res = integralGradeService.getById(id);
        return R.ok().data("record", res);
    }

    /**
     *
     * @param integralGrade 根据id修改积分等级
     * @return
     */
    @ApiOperation(value = "根据id修改积分等级")
    @PutMapping("/update")
    public R update(
            @ApiParam(value = "积分等级表单", required = true)
            @RequestBody IntegralGrade integralGrade
    ){

        Assert.notNull(integralGrade.getBorrowAmount(), ResponseEnum.BORROW_AMOUNT_NULL_ERROR);

        boolean result = integralGradeService.updateById(integralGrade);
        if (result){
            return R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }
}

