package com.arui.srb.oss.service;

import java.io.InputStream;

/**
 * @author ...
 */
public interface FileService {
    /**
     * 上传文件接口
     * @param inputStream 输入流
     * @param originalFilename 文件名
     * @param module 哪个模块调用
     * @return 返回oss的文件url
     */
    String upload(InputStream inputStream, String originalFilename, String module);

    /**
     * 删除文件
     * @param urlPath 传入的文件url
     */
    void delete(String urlPath);
}
