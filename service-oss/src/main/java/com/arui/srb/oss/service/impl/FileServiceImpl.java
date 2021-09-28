package com.arui.srb.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.arui.srb.oss.service.FileService;
import com.arui.srb.oss.util.PropertiesConfig;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author ...
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(InputStream inputStream, String originalFilename, String module) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(
                PropertiesConfig.ENDPOINT,
                PropertiesConfig.KEY_ID,
                PropertiesConfig.kEY_SECRET);

        // 存储到oss的路径名, = 模块+时间+uuid+后缀名
        String dateTime = new DateTime().toString("yyyy/MM/dd");
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String lastPath = originalFilename.substring(originalFilename.lastIndexOf("."));
        String ossPath = module + "/" + dateTime + "/" + uuid + lastPath;

        // 发送请求，上传文件到oos
        PutObjectResult result = ossClient.putObject(PropertiesConfig.BUCKET_NAME, ossPath, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
        String ossUrl = "https://" + PropertiesConfig.BUCKET_NAME + "." + PropertiesConfig.ENDPOINT +
                "/" + ossPath;

        // 返回url地址
        return ossUrl;
    }

    @Override
    public void delete(String urlPath) {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(
                PropertiesConfig.ENDPOINT,
                PropertiesConfig.KEY_ID,
                PropertiesConfig.kEY_SECRET);

        // 文件的域名
        String domain = "https://" + PropertiesConfig.BUCKET_NAME + "." + PropertiesConfig.ENDPOINT + "/";

        // 删除文件的路径名
        String filePath = urlPath.substring(domain.length());

        // 执行删除操作
        ossClient.deleteObject(PropertiesConfig.BUCKET_NAME, filePath);

        ossClient.shutdown();
    }
}
