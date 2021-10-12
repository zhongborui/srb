package com.arui.srb.core.controller.api;


import com.arui.common.exception.Assert;
import com.arui.common.result.R;
import com.arui.common.result.ResponseEnum;
import com.arui.srb.core.pojo.entity.Dict;
import com.arui.srb.core.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Api(tags = "前台数据字典接口")
@RestController
@RequestMapping("/api/core/dict")
public class ApiDictController {

    @Resource
    private DictService dictService;

    @ApiOperation(value = "查询该字典编码全部子类")
    @GetMapping("/findByDictCode/{dictCode}")
    public R findByDictCode(
            @ApiParam(value = "字典编码", required = true)
            @PathVariable String dictCode){

        List<Dict> dictList = dictService.findByDictCode(dictCode);
        return R.ok().data("dictList", dictList);
    }
}

