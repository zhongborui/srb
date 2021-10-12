package com.arui.srb.core.service;

import com.arui.srb.core.pojo.entity.Borrower;
import com.arui.srb.core.pojo.vo.BorrowerVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 借款人 服务类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
public interface BorrowerService extends IService<Borrower> {

    /**
     * 保存借款人额度申请信息
     * @param borrowerVO
     * @param userId
     */
    void saveBorrower(BorrowerVO borrowerVO, Long userId);

    /**
     * 根据userId查询认证状态
     * @param userId
     * @return
     */
    Integer getBorrowerStatusByUserId(Long userId);
}
