package com.arui.srb.oss.controller;

import com.arui.common.exception.BusinessException;
import com.arui.common.result.R;
import com.arui.common.result.ResponseEnum;
import com.arui.srb.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ...
 */

@RestController
@RequestMapping("/api/oss/file")
@Api(tags = "oss文件系统")
public class FileController {

    @Resource
    private FileService fileService;

    /**
     * 文件上传
     * @param file
     * @param module
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public R upload(
            @ApiParam(value = "上传文件", required = true)
            MultipartFile file,
            @ApiParam(value = "模块名", required = true)
            @RequestParam("module") String module
    ){
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String fileUrl = fileService.upload(inputStream, originalFilename, module);
            return R.ok().message("文件上传成功").data("fileUrl", fileUrl);
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    @ApiOperation("文件删除")
    @DeleteMapping("/delete")
    public R delete(
            @ApiParam(value = "文件url地址", required = true)
            @RequestParam("urlPath") String urlPath
    ){
        try {
            fileService.delete(urlPath);
            return R.ok().message("删除成功");
        } catch (Exception e) {
            throw new BusinessException("文件删除失败");
        }
    }
}
