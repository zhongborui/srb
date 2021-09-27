package com.arui.srb.sms;

import com.arui.srb.sms.utils.SmsProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ...
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UtilsTests {

    @Test
    public void propertiesTest(){
        System.out.println(SmsProperties.KEY_ID);
    }
}
