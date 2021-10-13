package com.arui.srb.core.service.impl;

import com.arui.common.exception.Assert;
import com.arui.common.result.ResponseEnum;
import com.arui.srb.core.enums.BorrowInfoEnum;
import com.arui.srb.core.enums.BorrowerEnum;
import com.arui.srb.core.mapper.IntegralGradeMapper;
import com.arui.srb.core.mapper.UserInfoMapper;
import com.arui.srb.core.pojo.entity.BorrowInfo;
import com.arui.srb.core.mapper.BorrowInfoMapper;
import com.arui.srb.core.pojo.entity.IntegralGrade;
import com.arui.srb.core.pojo.entity.UserInfo;
import com.arui.srb.core.service.BorrowInfoService;
import com.arui.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

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

    /**
     * 获得借款金额
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

        if (objects.size() == 0){
            return BorrowInfoEnum.UN_COMMIT.getStatus();
        }
        return (Integer)objects.get(0);
    }
}
