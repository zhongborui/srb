package com.arui.service;

import com.arui.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ...
 *
 */
public interface UserService extends IService<User> {
    public List<User> getUsersByName(String queryString);
}
