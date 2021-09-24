package com.arui;

import com.arui.entity.User;
import com.arui.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author ...
 *
 *  测试mybatis-plus的通用service
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestService {
    @Autowired
    private UserService userService;

    // 测试自定的查询方法
    @Test
    public void test05(){
        List<User> j = userService.getUsersByName("j");
        System.out.println(j);
    }

    // 删除
    @Test
    public void test04(){
        boolean b = userService.removeById(1437745045645025282L);
        System.out.println(b);
    }

    // 修改
    @Test
    public void test03(){
        User user = new User(1437745045645025282L, "mike", 22, null,null, null);
        boolean update = userService.updateById(user);
        System.out.println(update);
    }

    //插入一行数据
    @Test
    public void test02(){
//        User user = new User(null, "mike", 30, null, null, null);
        User user = new User();
        user.setName("hello");
        boolean save = userService.save(user);
        System.out.println(save);
    }

    //查询所有
    @Test
    public void test01(){
        List<User> list = userService.list();
        System.out.println(list);
    }
}
