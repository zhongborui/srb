package com.arui.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arui.srb.core.listener.DictExcelListener;
import com.arui.srb.core.pojo.dto.DictExcelDTO;
import com.arui.srb.core.pojo.entity.Dict;
import com.arui.srb.core.mapper.DictMapper;
import com.arui.srb.core.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    /**
     * mybatis-plus中 baseMapper 多态实际执行还是DictMapper
     * @param inputStream 文件导入输入流
     */
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, DictExcelDTO.class, new DictExcelListener(baseMapper))
                .sheet().doRead();
    }
}
