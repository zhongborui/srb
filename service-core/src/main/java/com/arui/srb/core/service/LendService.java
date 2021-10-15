package com.arui.srb.core.service;

import com.arui.srb.core.pojo.entity.BorrowInfo;
import com.arui.srb.core.pojo.entity.Lend;
import com.arui.srb.core.pojo.vo.BorrowInfoApprovalVO;
import com.arui.srb.core.pojo.vo.LendVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的准备表 服务类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
public interface LendService extends IService<Lend> {

    /**
     * 返回列表
     * @return
     */
    List<LendVO> getLendList();

    /**
     * 生成标的
     * @param borrowInfoApprovalVO
     * @param borrowInfo
     */
    void createLend(BorrowInfoApprovalVO borrowInfoApprovalVO, BorrowInfo borrowInfo);

    /**
     * 查看标的的详情信息
     * @param id
     * @return
     */
    Map<String, Object> getLendDetail(Long id);

    /**
     * 计算收益
     * @param invest
     * @param yearRate
     * @param totalmonth
     * @param returnMethod
     * @return
     */
    BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, Integer totalmonth, Integer returnMethod);
}
