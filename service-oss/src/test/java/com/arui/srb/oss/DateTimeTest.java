package com.arui.srb.oss;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.UUID;

/**
 * @author ...
 */
public class DateTimeTest {
    @Test
    public void testDateTime(){
        System.out.println(new DateTime().toString("yyyy/MM/dd"));
        System.out.println(new DateTime().toString("yyyy/mm/dd"));
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }
}
