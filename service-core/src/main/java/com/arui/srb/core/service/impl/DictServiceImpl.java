package com.arui.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arui.srb.core.listener.DictExcelListener;
import com.arui.srb.core.pojo.dto.DictExcelDTO;
import com.arui.srb.core.pojo.entity.Dict;
import com.arui.srb.core.mapper.DictMapper;
import com.arui.srb.core.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, DictExcelDTO.class, new DictExcelListener(baseMapper))
                .sheet().doRead();
    }

    /**
     * 导出数据
     * @return
     */
    @Override
    public List listDictData() {
        List<Dict> dictList = baseMapper.selectList(null);
        // 将List<Dict> 转换成 list<DictExcelDTO>
        List<DictExcelDTO> dtoList = new ArrayList<>();

        dictList.forEach( dict -> {
            DictExcelDTO dictExcelDTO = new DictExcelDTO();
            BeanUtils.copyProperties(dict, dictExcelDTO);
            dtoList.add(dictExcelDTO);
        });
        return dtoList;
    }

    @Override
    public List<Dict> listByParentId(Long parentId) {
        List<Dict> dictList = baseMapper.selectList(new QueryWrapper<Dict>().eq("parent_id", parentId));
        dictList.forEach(dict -> {
            //如果有子节点，则是非叶子节点
            boolean hasChildren = this.hasChildren(dict.getId());
            dict.setHasChildren(hasChildren);
        });
        return dictList;
    }

    /**
     * 判断该节点是否有子节点
     */
    private boolean hasChildren(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>().eq("parent_id", id);
        Integer count = baseMapper.selectCount(queryWrapper);
        if(count.intValue() > 0) {
            return true;
        }
        return false;
    }
}
