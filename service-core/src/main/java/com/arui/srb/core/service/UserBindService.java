package com.arui.srb.core.service;

import com.arui.srb.core.pojo.entity.UserBind;
import com.arui.srb.core.pojo.vo.UserBindVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
public interface UserBindService extends IService<UserBind> {

    /**
     * srb平台存入绑定相关信息（还未完成绑定，bind_status=0）
     * 后期，需要调用hfb平台接口以及异步调用更新绑定状态
     * @param userBindVO
     * @param userId
     * @return
     */
    String commitUserBind(UserBindVO userBindVO, Long userId);

    /**
     * srb用户绑定异步接口
     * @param paramMap
     */
    void bindNotify(Map<String, Object> paramMap);

    /**
     * 判断是否账户已经绑定，是返回false
     * @param paramMap
     * @return
     */
    boolean isBind(Map<String, Object> paramMap);

    /**
     * 绑定协议号
     * @param investUserId
     * @return
     */
    String getBindCodeByUserId(Long investUserId);
}
