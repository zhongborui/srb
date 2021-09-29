package com.arui.srb.sms;

import com.arui.srb.sms.utils.SmsProperties;
import net.minidev.json.JSONUtil;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import sun.management.snmp.jvmmib.JVM_MANAGEMENT_MIBOidTable;

/**
 * @author ...
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJasypt {
    /**
     * 测试jasypt
     */
    @Test
    public void test01(){
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword("hel");
        String encrypt = basicTextEncryptor.encrypt("你好");
        System.out.println(encrypt);
        System.out.println(basicTextEncryptor.decrypt(encrypt));
        System.out.println(SmsProperties.REGION_Id);
    }

}
