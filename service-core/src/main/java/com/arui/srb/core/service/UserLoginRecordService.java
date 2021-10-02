package com.arui.srb.core.service;

import com.arui.srb.core.pojo.entity.UserLoginRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
public interface UserLoginRecordService extends IService<UserLoginRecord> {

    /**
     * 查询用户前50前日志
     * @param userId
     * @return
     */
    List<UserLoginRecord> listTop50(Long userId);
}
