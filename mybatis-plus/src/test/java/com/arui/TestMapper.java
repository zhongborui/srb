package com.arui;

import com.arui.entity.User;
import com.arui.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ...
 *
 *  mybatis-plus通用Mapper测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMapper {
    @Autowired
    public UserMapper userMapper;

    //删除
    @Test
    public void test05(){
        int i = userMapper.deleteById(1437741315147100162L);
        System.out.println(i);
    }

    //根据id修改
    @Test
    public void test04(){
        User user = new User(1437741315147100162L, "jackey", 30, null,null, null);
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    //增加插入
    @Test
    public void test03(){
        User user = new User(null, "mike",20,null,null, null);
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void test02(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Jone");
        List<User> users = userMapper.selectByMap(map);
        System.out.println(users);
    }

    //查询所有
    @Test
    public void test01() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }


}
