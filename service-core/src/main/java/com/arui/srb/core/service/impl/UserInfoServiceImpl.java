package com.arui.srb.core.service.impl;

import com.arui.common.exception.Assert;
import com.arui.common.exception.BusinessException;
import com.arui.common.result.ResponseEnum;
import com.arui.common.util.MD5;
import com.arui.srb.core.pojo.entity.UserInfo;
import com.arui.srb.core.mapper.UserInfoMapper;
import com.arui.srb.core.pojo.vo.UserInfoVO;
import com.arui.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void register(UserInfoVO userInfoVO) {
        String mobile = userInfoVO.getMobile();
        // 判断用户是否已经注册
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("mobile", mobile);
        Integer selectCount = baseMapper.selectCount(userInfoQueryWrapper);
        Assert.isTrue(selectCount == 0, ResponseEnum.MOBILE_EXIST_ERROR);

        String registerCode = userInfoVO.getRegisterCode();
        // 取出redis中验证码校验是否正确
        String codeCache = (String) redisTemplate.opsForValue().get("srb:sms:code:" + mobile);

        Assert.isTrue(codeCache.equals(registerCode), ResponseEnum.CODE_ERROR);
        log.info("验证码校验成功，准备将注册信息写入数据库");
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserType(userInfoVO.getUserType());
            userInfo.setMobile(userInfoVO.getMobile());
            userInfo.setPassword(MD5.encrypt(userInfoVO.getPassword()));
            userInfo.setStatus(UserInfo.STATUS_NORMAL);
            userInfoMapper.insert(userInfo);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
