package com.arui.srb.core.service.impl;

import com.arui.common.exception.Assert;
import com.arui.common.result.ResponseEnum;
import com.arui.common.util.MD5;
import com.arui.srb.base.util.JwtUtils;
import com.arui.srb.core.mapper.UserLoginRecordMapper;
import com.arui.srb.core.pojo.entity.UserInfo;
import com.arui.srb.core.mapper.UserInfoMapper;
import com.arui.srb.core.pojo.entity.UserLoginRecord;
import com.arui.srb.core.pojo.query.UserInfoQuery;
import com.arui.srb.core.pojo.vo.LoginVO;
import com.arui.srb.core.pojo.vo.RegisterVO;
import com.arui.srb.core.pojo.vo.UserInfoVO;
import com.arui.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private UserLoginRecordMapper userLoginRecordMapper;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void register(RegisterVO registerVO) {
        String mobile = registerVO.getMobile();
        // 判断用户是否已经注册
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("mobile", mobile);
        Integer selectCount = baseMapper.selectCount(userInfoQueryWrapper);
        Assert.isTrue(selectCount == 0, ResponseEnum.MOBILE_EXIST_ERROR);

        String registerCode = registerVO.getCode();
        // 取出redis中验证码校验是否正确
        String codeCache = (String) redisTemplate.opsForValue().get("srb:sms:code:" + mobile);

        Assert.isTrue(codeCache.equals(registerCode), ResponseEnum.CODE_ERROR);
        log.info("验证码校验成功，准备将注册信息写入数据库");
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserType(registerVO.getUserType());
            userInfo.setMobile(registerVO.getMobile());
            userInfo.setPassword(MD5.encrypt(registerVO.getPassword()));
            userInfo.setStatus(UserInfo.STATUS_NORMAL);
            userInfoMapper.insert(userInfo);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public UserInfoVO login(LoginVO loginVO, String ip) {
        Integer userType = loginVO.getUserType();
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();

        // 获取会员
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("user_type", userType)
                .eq("mobile", mobile);
        UserInfo userInfo = baseMapper.selectOne(userInfoQueryWrapper);

        // LOGIN_MOBILE_ERROR(208, "用户不存在"),
        Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);

        // LOGIN_PASSWORD_ERROR(209, "密码错误"),
        String dbPassword = userInfo.getPassword();
        String loginPassword = MD5.encrypt(password);
        Assert.isTrue(dbPassword.equals(loginPassword), ResponseEnum.LOGIN_PASSWORD_ERROR);

        // 查看用户是否被禁用
        Assert.equals(userInfo.getStatus(), UserInfo.STATUS_NORMAL, ResponseEnum.LOGIN_LOKED_ERROR);

        // 将日志插入到日志表
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setIp(ip);
        userLoginRecord.setUserId(userInfo.getId());
        userLoginRecordMapper.insert(userLoginRecord);

        // 生成token
        String token = JwtUtils.createToken(userInfo.getId(), userInfo.getName());
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setToken(token);
        userInfoVO.setName(userInfo.getName());
        userInfoVO.setNickName(userInfo.getNickName());
        userInfoVO.setHeadImg(userInfo.getHeadImg());
        userInfoVO.setMobile(userInfo.getMobile());
        userInfoVO.setUserType(userType);
        return userInfoVO;
    }

    @Override
    public Page<UserInfo> listPage(Page<UserInfo> userInfoPage, UserInfoQuery userInfoQuery) {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        String mobile = userInfoQuery.getMobile();
        Integer userType = userInfoQuery.getUserType();
        Integer status = userInfoQuery.getStatus();

        userInfoQueryWrapper
                .eq(StringUtils.isNotBlank(mobile),"mobile", mobile)
                .eq(userType != null, "user_type", userType)
                .eq(status != null, "status", status);
        Page<UserInfo> userListPage = userInfoMapper.selectPage(userInfoPage, userInfoQueryWrapper);
//        Page<UserInfo> userListPage = baseMapper.selectPage(userInfoPage, userInfoQueryWrapper);
        return userListPage;
    }

    @Override
    public void lock(Long id, Integer status) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setStatus(status);
        baseMapper.updateById(userInfo);
    }

    @Override
    public boolean checkMobile(String mobile) {
        QueryWrapper<UserInfo> userInfoServiceQueryWrapper = new QueryWrapper<>();
        userInfoServiceQueryWrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(userInfoServiceQueryWrapper);
        // true是手机号已经注册
        return count > 0;
    }

//    @Override
//    public List<UserInfo> listByUserInfoQuery(UserInfoQuery userInfoQuery) {
//        QueryWrapper<UserInfo> userInfoQueryQueryWrapper = new QueryWrapper<>();
//        userInfoQueryQueryWrapper.eq("mobile", userInfoQuery.getMobile())
//                .eq("user_type", userInfoQuery.getUserType())
//                .eq("status", userInfoQuery.getStatus());
//
//        List<UserInfo> userInfoList = userInfoMapper.selectList(userInfoQueryQueryWrapper);
//        return userInfoList;
//    }
}
