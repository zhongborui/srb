package com.arui.srb.core.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.arui.common.exception.BusinessException;
import com.arui.common.result.R;
import com.arui.common.result.ResponseEnum;
import com.arui.srb.core.pojo.dto.DictExcelDTO;
import com.arui.srb.core.pojo.entity.Dict;
import com.arui.srb.core.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ...
 */
@RestController
@CrossOrigin
@Api(tags = "数据字典管理接口")
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

    @ApiOperation("导出数据字典")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), DictExcelDTO.class)
                    .sheet("数据字典").doWrite(dictService.listDictData());
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.EXPORT_DATA_ERROR, e);
        }
    }

    @ApiOperation("根据上级id获取子节点数据列表")
    @GetMapping("/listByParentId/{parentId}")
    public R listByParentId(
            @ApiParam(value = "上级节点id", required = true)
            @PathVariable("parentId") Long parentId){
        List<Dict> dictList = dictService.listByParentId(parentId);
        return R.ok().data("list", dictList);
    }

}
