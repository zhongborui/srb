package com.arui.srb.core.service;

import com.arui.srb.core.pojo.entity.BorrowInfo;
import com.arui.srb.core.pojo.vo.BorrowInfoApprovalVO;
import com.arui.srb.core.pojo.vo.BorrowInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    /**
     * 获得借款人列表
     * @return
     */
    List<BorrowInfoVO> getUserInfoList();

    /**
     * 根据id查询借款人借款信息
     * @param id
     * @return
     */
    Map<String, Object> show(Long id);

    /**
     * 审核借款申请
     * @param borrowInfoApprovalVO
     */
    void approval(BorrowInfoApprovalVO borrowInfoApprovalVO);
}
