package com.arui.srb.core.controller.admin;

import com.arui.common.exception.BusinessException;
import com.arui.common.result.R;
import com.arui.common.result.ResponseEnum;
import com.arui.srb.core.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author ...
 */
@RestController
@CrossOrigin
@Api(tags = "数据字典Excel读写模块")
@RequestMapping("/admin/core/dict")
public class AdminDictController {

    @Resource
    private DictService dictService;

    /**
     * 将上传的数据字典excel数据保存到数据库
     * @param file 上传的数据字典excel
     * @return
     */
    @ApiOperation("导入数据字典Excel")
    @PostMapping("/import")
    public R importData(
            @ApiParam(value = "file", required = true)
            @RequestParam("file") MultipartFile file
    ){
        try {
            dictService.importData(file.getInputStream());
            return R.ok().message("上传文件成功");
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

}
