package com.arui.srb.core.service;

import com.arui.srb.core.pojo.entity.LendItem;
import com.arui.srb.core.pojo.vo.InvestVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 标的出借记录表 服务类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
public interface LendItemService extends IService<LendItem> {

    /**
     * 返回表单，调用第三方接口
     * @param investVO
     * @return
     */
    String commitInvest(InvestVO investVO);

    /**
     * 投资异步回调
     * @param paramMap
     */
    void investNotify(Map<String, Object> paramMap);
}
