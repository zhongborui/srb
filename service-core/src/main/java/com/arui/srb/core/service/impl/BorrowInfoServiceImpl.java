package com.arui.srb.core.service.impl;

import com.arui.common.exception.Assert;
import com.arui.common.result.ResponseEnum;
import com.arui.srb.core.enums.BorrowInfoEnum;
import com.arui.srb.core.enums.BorrowInfoStatusEnum;
import com.arui.srb.core.enums.ReturnMethodEnum;
import com.arui.srb.core.mapper.*;
import com.arui.srb.core.pojo.entity.BorrowInfo;
import com.arui.srb.core.pojo.entity.Borrower;
import com.arui.srb.core.pojo.entity.IntegralGrade;
import com.arui.srb.core.pojo.entity.UserInfo;
import com.arui.srb.core.pojo.vo.BorrowInfoApprovalVO;
import com.arui.srb.core.pojo.vo.BorrowInfoVO;
import com.arui.srb.core.pojo.vo.BorrowerDetailVO;
import com.arui.srb.core.service.BorrowInfoService;
import com.arui.srb.core.service.BorrowerService;
import com.arui.srb.core.service.DictService;
import com.arui.srb.core.service.LendService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 借款信息表 服务实现类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Service
public class BorrowInfoServiceImpl extends ServiceImpl<BorrowInfoMapper, BorrowInfo> implements BorrowInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private IntegralGradeMapper integralGradeMapper;

    @Resource
    private DictService dictService;

    @Resource
    private BorrowerMapper borrowerMapper;

    @Resource
    private BorrowerService borrowerService;

    @Resource
    private LendService lendService;

    /**
     * 获得借款金额
     *
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getBorrowAmount(Long userId) {
        // 获得用户积分
        UserInfo userInfo = userInfoMapper.selectById(userId);
        Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);
        Integer integral = userInfo.getIntegral();

        // integral_grade 查询可借款金额
        QueryWrapper<IntegralGrade> integralGradeQueryWrapper = new QueryWrapper<>();
        integralGradeQueryWrapper.lt("integral_start", integral)
                .ge("integral_end", integral);
        IntegralGrade integralGrade = integralGradeMapper.selectOne(integralGradeQueryWrapper);
        BigDecimal borrowAmount = integralGrade.getBorrowAmount();
        return borrowAmount;
    }

    @Override
    public void saveBorrowInfo(BorrowInfo borrowInfo) {

        // 一系列判断

        borrowInfo.setStatus(BorrowInfoEnum.APPROVING.getStatus());
        BigDecimal borrowYearRate = borrowInfo.getBorrowYearRate().divide(new BigDecimal("100"));
        borrowInfo.setBorrowYearRate(borrowYearRate);
        baseMapper.insert(borrowInfo);
    }

    @Override
    public Integer getBorrowInfoStatus(Long userId) {
        QueryWrapper<BorrowInfo> borrowInfoQueryWrapper = new QueryWrapper<>();
        borrowInfoQueryWrapper.select("status").eq("user_id", userId);
        List<Object> objects = baseMapper.selectObjs(borrowInfoQueryWrapper);

        if (objects.size() == 0) {
            return BorrowInfoEnum.UN_COMMIT.getStatus();
        }
        return (Integer) objects.get(0);
    }

    @Override
    public List<BorrowInfoVO> getUserInfoList() {
        List<BorrowInfoVO> borrowInfoVOList = baseMapper.getUserInfoList();

        borrowInfoVOList.forEach(borrowInfoVO -> {
            HashMap<String, Object> param = new HashMap<>();
            borrowInfoVO.setParam(param);
            // 还款方式
            String returnMethod = dictService.getNameByDictCodeAndValue("returnMethod", borrowInfoVO.getReturnMethod());
            borrowInfoVO.getParam().put("returnMethod", returnMethod);
            // 资金用途
            String moneyUse = dictService.getNameByDictCodeAndValue("moneyUse", borrowInfoVO.getMoneyUse());
            borrowInfoVO.getParam().put("moneyUse", moneyUse);
            // 状态
            borrowInfoVO.getParam().put("status", BorrowInfoStatusEnum.getMsgByStatus(borrowInfoVO.getStatus()));
        });

        return borrowInfoVOList;
    }

    @Override
    public Map<String, Object> show(Long id) {
        HashMap<String, Object> borrowInfoDetail = new HashMap<>();
        BorrowInfoVO borrowInfoVO = baseMapper.getUserInfoById(id);
        // 还款方式
        HashMap<String, Object> param = new HashMap<>();
        borrowInfoVO.setParam(param);
        Integer returnMethod1 = borrowInfoVO.getReturnMethod();
        String returnMethod = dictService.getNameByDictCodeAndValue("returnMethod",returnMethod1 );
        borrowInfoVO.getParam().put("returnMethod", returnMethod);
        // 资金用途
        String moneyUse = dictService.getNameByDictCodeAndValue("moneyUse", borrowInfoVO.getMoneyUse());
        borrowInfoVO.getParam().put("moneyUse", moneyUse);
        // 状态
        borrowInfoVO.getParam().put("status", BorrowInfoStatusEnum.getMsgByStatus(borrowInfoVO.getStatus()));
        borrowInfoDetail.put("borrowInfo", borrowInfoVO);

        BorrowerDetailVO borrowerDetailVO = borrowerService.showBorrowDetailByUserId(id);
        borrowInfoDetail.put("borrower", borrowerDetailVO);
        return borrowInfoDetail;
    }

    @Override
    public void approval(BorrowInfoApprovalVO borrowInfoApprovalVO) {
        Long id = borrowInfoApprovalVO.getId();
        BorrowInfo borrowInfo = baseMapper.selectById(id);
        borrowInfo.setStatus(BorrowInfoStatusEnum.CHECK_OK.getStatus());
        baseMapper.updateById(borrowInfo);

        //审核通过则创建标的
        if (borrowInfoApprovalVO.getStatus().intValue() == BorrowInfoStatusEnum.CHECK_OK.getStatus().intValue()) {
            //创建标的
            lendService.createLend(borrowInfoApprovalVO, borrowInfo);
        }
    }
}
