package com.arui.srb.core.service;

import com.arui.srb.core.pojo.entity.UserAccount;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 用户账户 服务类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
public interface UserAccountService extends IService<UserAccount> {

    /**
     * 投资人充值接口
     * @param chargeAmt
     * @param userId
     * @return
     */
    String charge(BigDecimal chargeAmt, Long userId);

    /**
     * 充值回调接口
     * @param paramMap
     */
    void chargeNotify(Map<String, Object> paramMap);

    /**
     * 查询余额账户
     * @param userId
     * @return
     */
    BigDecimal getAccount(Long userId);
}
