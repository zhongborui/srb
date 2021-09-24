package com.arui.mapper;

import com.arui.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ...
 * mybatis-plus通用mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 自定义的查询
     * @param queryString
     * @return
     */
    public List<User> selectUsersByName(String queryString);
}
