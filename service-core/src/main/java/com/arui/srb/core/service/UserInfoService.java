package com.arui.srb.core.service;

import com.arui.srb.core.pojo.entity.UserInfo;
import com.arui.srb.core.pojo.vo.UserInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 用户注册
     * @param userInfoVO 用户的注册信息表单
     */
    void register(UserInfoVO userInfoVO);
}
