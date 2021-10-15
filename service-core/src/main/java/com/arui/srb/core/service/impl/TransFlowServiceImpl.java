package com.arui.srb.core.service.impl;

import com.arui.srb.core.mapper.UserBindMapper;
import com.arui.srb.core.mapper.UserInfoMapper;
import com.arui.srb.core.pojo.bo.TransFlowBO;
import com.arui.srb.core.pojo.entity.TransFlow;
import com.arui.srb.core.mapper.TransFlowMapper;
import com.arui.srb.core.pojo.entity.UserBind;
import com.arui.srb.core.pojo.entity.UserInfo;
import com.arui.srb.core.service.TransFlowService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sun.text.normalizer.UBiDiProps;

import javax.annotation.Resource;

/**
 * <p>
 * 交易流水表 服务实现类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Service
public class TransFlowServiceImpl extends ServiceImpl<TransFlowMapper, TransFlow> implements TransFlowService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserBindMapper userBindMapper;

    @Override
    public void saveTransFlow(TransFlowBO transFlowBO) {
        //获取用户基本信息 user_info
        QueryWrapper<UserBind> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("bind_code", transFlowBO.getBindCode());
        UserBind userInfo = userBindMapper.selectOne(userInfoQueryWrapper);

        //存储交易流水数据
        TransFlow transFlow = new TransFlow();
        transFlow.setUserId(userInfo.getId());
        transFlow.setUserName(userInfo.getName());
        transFlow.setTransNo(transFlowBO.getAgentBillNo());
        transFlow.setTransType(transFlowBO.getTransTypeEnum().getTransType());
        transFlow.setTransTypeName(transFlowBO.getTransTypeEnum().getTransTypeName());
        transFlow.setTransAmount(transFlowBO.getAmount());
        transFlow.setMemo(transFlowBO.getMemo());
        baseMapper.insert(transFlow);
    }
}
