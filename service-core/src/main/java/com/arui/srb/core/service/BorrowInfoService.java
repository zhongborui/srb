package com.arui.srb.core.service;

import com.arui.srb.core.pojo.entity.BorrowInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 借款信息表 服务类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
public interface BorrowInfoService extends IService<BorrowInfo> {

    /**
     * 获得借款金额
     * @param userId
     * @return
     */
    BigDecimal getBorrowAmount(Long userId);

    /**
     * 保存借款人借款申请信息
     * @param borrowInfo
     */
    void saveBorrowInfo(BorrowInfo borrowInfo);

    /**
     * 获得借款人借款申请状态
     * @param userId
     * @return
     */
    Integer getBorrowInfoStatus(Long userId);
}
