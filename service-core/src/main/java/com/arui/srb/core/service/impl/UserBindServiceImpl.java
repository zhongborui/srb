package com.arui.srb.core.service.impl;

import com.arui.srb.core.enums.UserBindEnum;
import com.arui.srb.core.hfb.FormHelper;
import com.arui.srb.core.hfb.HfbConst;
import com.arui.srb.core.hfb.RequestHelper;
import com.arui.srb.core.mapper.UserInfoMapper;
import com.arui.srb.core.pojo.entity.UserBind;
import com.arui.srb.core.mapper.UserBindMapper;
import com.arui.srb.core.pojo.entity.UserInfo;
import com.arui.srb.core.pojo.vo.UserBindVO;
import com.arui.srb.core.service.UserBindService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务实现类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Service
public class UserBindServiceImpl extends ServiceImpl<UserBindMapper, UserBind> implements UserBindService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public String commitUserBind(UserBindVO userBindVO, Long userId) {
        // user_bind表插入数据，如果userId已存在数据更新操作
        QueryWrapper<UserBind> userBindQueryWrapper = new QueryWrapper<>();
        userBindQueryWrapper.eq("user_id", userId);
        UserBind userBind = baseMapper.selectOne(userBindQueryWrapper);
        if (userBind == null){
            // 该userId没有绑定数据，直接插入
            UserBind bind = new UserBind();
            BeanUtils.copyProperties(userBindVO, bind);
            bind.setUserId(userId);
            bind.setStatus(UserBindEnum.NO_Bind.getCode());
            baseMapper.insert(bind);
        }else {
            // 更新已经存在的数据
            BeanUtils.copyProperties(userBindVO, userBind);
            baseMapper.updateById(userBind);
        }

        // 调用hfb接口返回formStr
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentUserId", userId);
        paramMap.put("idCard",userBindVO.getIdCard());
        paramMap.put("personalName", userBindVO.getName());
        paramMap.put("bankType", userBindVO.getBankType());
        paramMap.put("bankNo", userBindVO.getBankNo());
        paramMap.put("mobile", userBindVO.getMobile());
        paramMap.put("returnUrl", HfbConst.USERBIND_RETURN_URL);
        paramMap.put("notifyUrl", HfbConst.USERBIND_NOTIFY_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        paramMap.put("sign", RequestHelper.getSign(paramMap));

        String formStr = FormHelper.buildForm(HfbConst.USERBIND_URL, paramMap);
        return formStr;
    }

    @Override
    public void bindNotify(Map<String, Object> paramMap) {
        // user_bind表
        QueryWrapper<UserBind> userBindQueryWrapper = new QueryWrapper<>();
        userBindQueryWrapper.eq("user_id", paramMap.get("agentUserId"));
        UserBind userBind = baseMapper.selectOne(userBindQueryWrapper);
        userBind.setStatus(UserBindEnum.OK_Bind.getCode());
        String bindCode = (String) paramMap.get("bindCode");
        userBind.setBindCode(bindCode);
        baseMapper.updateById(userBind);

        // user_info表
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("user_id", paramMap.get("agentUserId"));
        UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper);
        userInfo.setBindCode(bindCode);
        userInfo.setBindStatus(UserBindEnum.OK_Bind.getCode());
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public boolean isBind(Map<String, Object> paramMap) {
        QueryWrapper<UserBind> userBindQueryWrapper = new QueryWrapper<>();
        userBindQueryWrapper.select("status")
                .eq("user_id", paramMap.get("agentUserId"));
        UserBind userBind = baseMapper.selectOne(userBindQueryWrapper);
        if (userBind.getStatus().equals(UserBindEnum.OK_Bind.getCode())){
            return false;
        }
        return true;
    }
}
