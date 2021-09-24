package com.arui.service.impl;

import com.arui.entity.User;
import com.arui.mapper.UserMapper;
import com.arui.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ...
 *  mybatis-plus通用Service
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<User> getUsersByName(String queryString) {
        return baseMapper.selectUsersByName(queryString);
    }
}
