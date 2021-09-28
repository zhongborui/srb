package com.arui.srb.oss.util;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ...
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class PropertiesConfig implements InitializingBean {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    public static String ENDPOINT;
    public static String KEY_ID;
    public static String kEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endpoint;
        KEY_ID = accessKeyId;
        kEY_SECRET = accessKeySecret;
        BUCKET_NAME = bucketName;
    }
}
