package com.arui.srb.core.mapper;

import com.arui.srb.core.pojo.entity.BorrowInfo;
import com.arui.srb.core.pojo.vo.BorrowInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 借款信息表 Mapper 接口
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
public interface BorrowInfoMapper extends BaseMapper<BorrowInfo> {

    /**
     * 自定义联合查询 borrow_info 和 borrow 表
     * @return
     */
    List<BorrowInfoVO> getUserInfoList();

    /**
     * 自定义
     * @return
     * @param id
     */
    BorrowInfoVO getUserInfoById(Long id);
}
