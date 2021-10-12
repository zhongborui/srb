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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.plugin.extension.ExtensionUtils;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Resource
    private RedisTemplate redisTemplate;


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

    /**
     * 展示数据字典列表
     * @param parentId
     * @return
     */
    @Override
    public List<Dict> listByParentId(Long parentId) {
        List<Dict> dictList = null;

        try {
            dictList  = (List<Dict>) redisTemplate.opsForValue().get("srb:core:dictList:" + parentId);
            if (dictList != null || dictList.size() >= 0){
                log.info("从redis中取值");
                return dictList;
            }
        } catch (Exception e) {
            // 为不影响操作，继续执行后面代码从数据库获取数据
            log.error("redis服务器异常" + ExceptionUtils.getStackTrace(e));
        }

        dictList = baseMapper.selectList(new QueryWrapper<Dict>().eq("parent_id", parentId));
        dictList.forEach(dict -> {
            //如果有子节点，则是非叶子节点
            boolean hasChildren = this.hasChildren(dict.getId());
            dict.setHasChildren(hasChildren);
        });

        // 将数据存入到redis
        try {
            redisTemplate.opsForValue().set("srb:core:dictList:" + parentId, dictList, 5, TimeUnit.MINUTES);
            log.info("数据存入redis");
        } catch (Exception e) {
            log.error("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
        }

        return dictList;
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {

        // 获取dictCode的对应id，及子类的parent_id
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.select("id").eq("dict_code", dictCode);
        Dict dict = baseMapper.selectOne(dictQueryWrapper);
        Long id = dict.getId();

        // 查询所有子类
        QueryWrapper<Dict> dictQueryWrapper1 = new QueryWrapper<>();
        dictQueryWrapper1.select("name", "value").eq("parent_id", id);
        List<Dict> dictList = baseMapper.selectList(dictQueryWrapper1);
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
