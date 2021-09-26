package com.arui.srb.core.service;

import com.arui.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;

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
}
