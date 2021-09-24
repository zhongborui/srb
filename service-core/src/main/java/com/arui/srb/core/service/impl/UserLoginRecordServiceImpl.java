package com.arui.srb.core.service.impl;

import com.arui.srb.core.pojo.entity.UserLoginRecord;
import com.arui.srb.core.mapper.UserLoginRecordMapper;
import com.arui.srb.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {

}
