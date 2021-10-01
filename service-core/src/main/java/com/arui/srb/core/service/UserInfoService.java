package com.arui.srb.core.service;

import com.arui.srb.core.pojo.entity.UserInfo;
import com.arui.srb.core.pojo.vo.LoginVO;
import com.arui.srb.core.pojo.vo.RegisterVO;
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
     * @param registerVO 用户的注册信息表单
     */
    void register(RegisterVO registerVO);

    /**
     * 用户登录
     * @param loginVO
     * @param ip
     * @return
     */
    UserInfoVO login(LoginVO loginVO, String ip);
}
