package com.arui.srb.core.service.impl;

import com.arui.srb.core.pojo.entity.UserAccount;
import com.arui.srb.core.mapper.UserAccountMapper;
import com.arui.srb.core.service.UserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

}
