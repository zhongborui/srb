package com.arui.srb.core.service;

import com.arui.srb.core.pojo.entity.UserInfo;
import com.arui.srb.core.pojo.query.UserInfoQuery;
import com.arui.srb.core.pojo.vo.LoginVO;
import com.arui.srb.core.pojo.vo.RegisterVO;
import com.arui.srb.core.pojo.vo.UserInfoVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    /**
     * 分页查询会员列表
     * @param userInfoPage 分页对象
     * @param userInfoQuery 查询条件
     * @return
     */
    Page<UserInfo> listPage(Page<UserInfo> userInfoPage, UserInfoQuery userInfoQuery);

    /**
     * 锁定用户
     * @param id
     * @param status
     */
    void lock(Long id, Integer status);

    /**
     * 检查手机号是否已经注册
     * @param mobile
     * @return
     */
    boolean checkMobile(String mobile);
}
