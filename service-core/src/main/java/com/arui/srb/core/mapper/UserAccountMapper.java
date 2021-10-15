package com.arui.srb.core.mapper;

import com.arui.srb.core.pojo.entity.UserAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;

/**
 * <p>
 * 用户账户 Mapper 接口
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {

    /**
     * 自定义更新account表
     * @param bindCode
     * @param chargeAmt
     * @param freezeAmount
     */
    void updateAmount(String bindCode, BigDecimal chargeAmt, BigDecimal freezeAmount);

}
