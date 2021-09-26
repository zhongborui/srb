package com.arui.srb.core.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arui.srb.core.mapper.DictMapper;
import com.arui.srb.core.pojo.dto.DictExcelDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ...
 */
@Slf4j
public class DictExcelListener extends AnalysisEventListener<DictExcelDTO> {

    private static final int BATCH_COUNT = 5;
    /**
     * 存储每一批数据的list
     */
    private List<DictExcelDTO> list = new ArrayList<>();

    private DictMapper dictMapper;

    /**
     *  有个很重要的点 Listener 不能被spring管理，
     *  要每次读取excel都要new,然后里面用到spring可以构造方法传进去
     *
     *  如果使用了spring,请使用这个构造方法。
     *  每次创建Listener的时候需要把spring管理的类传进来
     * @param dictMapper
     */
    public DictExcelListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    public DictExcelListener() {
    }

    /**
     * 每解析一条数据都会调用这个回调函数处理数据
     * @param data
     * @param context
     */
    @Override
    public void invoke(DictExcelDTO data, AnalysisContext context) {
        list.add(data);
        if (list.size() >= BATCH_COUNT){
            log.info("开始保存一条数据");
            saveData(list);
            log.info("保存一条数据");
            // 清空list
            list.clear();
        }
    }

    /**
     * 调用mapper将数据
     * 批量insert数据到数据库
     * @param list
     */
    private void saveData(List<DictExcelDTO> list) {
        dictMapper.insertBatch(list);
    }

    /**
     * 收尾工作，保存剩余的数据
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData(list);
        log.info("上传数据完成");
    }
}
