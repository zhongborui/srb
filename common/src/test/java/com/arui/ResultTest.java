package com.arui;

import com.arui.common.result.ResponseEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ...
 */
public class ResultTest {
    @Test
    public void test01(){
        System.out.println(ResponseEnum.ERROR.toString());
    }
}
