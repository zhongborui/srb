package com.arui.srb.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CreateBucketRequest;
import com.arui.srb.oss.util.PropertiesConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ...
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OssTest {

    @Test
    public void testCreateBucket(){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(PropertiesConfig.ENDPOINT, PropertiesConfig.KEY_ID, PropertiesConfig.kEY_SECRET);

        // 创建CreateBucketRequest对象。

        CreateBucketRequest arui02 = new CreateBucketRequest("arui02");

        // 创建存储空间。
        Bucket bucket = ossClient.createBucket(arui02);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
