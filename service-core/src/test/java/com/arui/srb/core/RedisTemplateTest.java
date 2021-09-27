package com.arui.srb.core;

import com.arui.srb.core.mapper.DictMapper;
import com.arui.srb.core.pojo.entity.Dict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ...
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private DictMapper dictMapper;
    @Test
    public void saveDict(){
        Dict dict = dictMapper.selectById(1);
        //向数据库中存储string类型的键值对, 过期时间5分钟
        redisTemplate.opsForValue().set("dict", dict, 5, TimeUnit.MINUTES);
    }

    @Test
    public void test01(){
        redisTemplate.boundValueOps("k11").set("v11");

        Dict key1 = (Dict) redisTemplate.boundValueOps("dict").get();
        System.out.println(key1);
    }
}
