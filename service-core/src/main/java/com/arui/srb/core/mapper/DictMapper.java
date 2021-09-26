package com.arui.srb.core.mapper;

import com.arui.srb.core.pojo.dto.DictExcelDTO;
import com.arui.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 批量插入数据
     * @param list
     */
    void insertBatch(List<DictExcelDTO> list);
}
