package com.arui.srb.core.service.impl;

import com.arui.srb.core.enums.TransTypeEnum;
import com.arui.srb.core.hfb.FormHelper;
import com.arui.srb.core.hfb.HfbConst;
import com.arui.srb.core.hfb.RequestHelper;
import com.arui.srb.core.mapper.UserBindMapper;
import com.arui.srb.core.pojo.bo.TransFlowBO;
import com.arui.srb.core.pojo.entity.UserAccount;
import com.arui.srb.core.mapper.UserAccountMapper;
import com.arui.srb.core.pojo.entity.UserBind;
import com.arui.srb.core.service.TransFlowService;
import com.arui.srb.core.service.UserAccountService;
import com.arui.srb.core.utils.LendNoUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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

    @Resource
    private UserBindMapper userBindMapper;

    @Resource
    private TransFlowService transFlowService;

    @Override
    public String charge(BigDecimal chargeAmt, Long userId) {
        UserBind userBind = userBindMapper.selectById(userId);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID );
        paramMap.put("agentBillNo", LendNoUtils.getChargeNo());
        paramMap.put("bindCode", userBind.getBindCode());
        paramMap.put("chargeAmt",chargeAmt );
        paramMap.put("feeAmt",new BigDecimal("0") );
        paramMap.put("notifyUrl",HfbConst.RECHARGE_NOTIFY_URL);
        paramMap.put("returnUrl",HfbConst.RECHARGE_RETURN_URL );
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        paramMap.put("sign", RequestHelper.getSign(paramMap));

        String formStr = FormHelper.buildForm(HfbConst.RECHARGE_URL, paramMap);
        return formStr;
    }

    @Override
    public void chargeNotify(Map<String, Object> paramMap) {
        String bindCode = (String)paramMap.get("bindCode");
        String chargeAmt = (String) paramMap.get("chargeAmt");

        //  更新account表
        baseMapper.updateAmount(bindCode, new BigDecimal(chargeAmt), new BigDecimal("0"));

        //增加交易流水
        String agentBillNo = (String)paramMap.get("agentBillNo");
        TransFlowBO transFlowBO = new TransFlowBO(
                agentBillNo,
                bindCode,
                new BigDecimal(chargeAmt),
                TransTypeEnum.RECHARGE,
                "充值");
        transFlowService.saveTransFlow(transFlowBO);
    }
}
