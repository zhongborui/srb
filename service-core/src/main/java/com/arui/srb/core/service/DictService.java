package com.arui.srb.core.service;

import com.arui.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
public interface DictService extends IService<Dict> {

    /**
     * 导入数据字典excel数据
     * @param inputStream 文件导入输入流
     */
    void importData(InputStream inputStream);

    /**
     * 导出数据字典
     * @return
     */
    List listDictData();

    /**
     * 根据上级id查询所有数据
     * @param parentId
     * @return
     */
    List<Dict> listByParentId(Long parentId);
}
