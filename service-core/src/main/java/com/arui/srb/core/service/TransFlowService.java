package com.arui.srb.core.service;

import com.arui.srb.core.pojo.bo.TransFlowBO;
import com.arui.srb.core.pojo.entity.TransFlow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 交易流水表 服务类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
public interface TransFlowService extends IService<TransFlow> {

    /**
     * 充值流水
     * @param transFlowBO
     */
    void saveTransFlow(TransFlowBO transFlowBO);

}
