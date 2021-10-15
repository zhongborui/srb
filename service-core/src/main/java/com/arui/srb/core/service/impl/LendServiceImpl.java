package com.arui.srb.core.service.impl;

import com.arui.srb.core.enums.LendStatusEnum;
import com.arui.srb.core.enums.ReturnMethodEnum;
import com.arui.srb.core.pojo.entity.BorrowInfo;
import com.arui.srb.core.pojo.entity.Borrower;
import com.arui.srb.core.pojo.entity.Lend;
import com.arui.srb.core.mapper.LendMapper;
import com.arui.srb.core.pojo.vo.BorrowInfoApprovalVO;
import com.arui.srb.core.pojo.vo.LendVO;
import com.arui.srb.core.service.BorrowerService;
import com.arui.srb.core.service.DictService;
import com.arui.srb.core.service.LendService;
import com.arui.srb.core.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.record.pivottable.StreamIDRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 标的准备表 服务实现类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Service
public class LendServiceImpl extends ServiceImpl<LendMapper, Lend> implements LendService {

    @Resource
    private DictService dictService;

    @Resource
    private BorrowerService borrowerService;

    @Override
    public List<LendVO> getLendList() {
        List<Lend> list = baseMapper.selectList(null);
        List<LendVO> lendVOList = list.stream().map(lend -> {
            HashMap<String, Object> map = new HashMap<>();
            LendVO lendVO = new LendVO();
            BeanUtils.copyProperties(lend,lendVO);
            lendVO.setParam(map);
            lendVO.getParam().put("returnMethod", dictService.getNameByDictCodeAndValue("returnMethod", lend.getReturnMethod()));
            lendVO.getParam().put("status", LendStatusEnum.getMsgByStatus(lend.getStatus()));
            return lendVO;
        }).collect(Collectors.toList());

        return lendVOList;
    }

    @Override
    public void createLend(BorrowInfoApprovalVO borrowInfoApprovalVO, BorrowInfo borrowInfo) {
        Lend lend = new Lend();
        lend.setUserId(borrowInfo.getUserId());
        lend.setBorrowInfoId(borrowInfo.getId());
        lend.setLendNo(LendNoUtils.getLendNo());//生成编号
        lend.setTitle(borrowInfoApprovalVO.getTitle());
        lend.setAmount(borrowInfo.getAmount());
        lend.setPeriod(borrowInfo.getPeriod());
        lend.setLendYearRate(borrowInfoApprovalVO.getLendYearRate().divide(new BigDecimal(100)));//从审批对象中获取
        lend.setServiceRate(borrowInfoApprovalVO.getServiceRate().divide(new BigDecimal(100)));//从审批对象中获取
        lend.setReturnMethod(borrowInfo.getReturnMethod());
        lend.setLowestAmount(new BigDecimal(100));
        lend.setInvestAmount(new BigDecimal(0));
        lend.setInvestNum(0);
        lend.setPublishDate(LocalDateTime.now());

        //起息日期
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate lendStartDate = LocalDate.parse(borrowInfoApprovalVO.getLendStartDate(), dtf);
        lend.setLendStartDate(lendStartDate);
        //结束日期
        LocalDate lendEndDate = lendStartDate.plusMonths(borrowInfo.getPeriod());
        lend.setLendEndDate(lendEndDate);

        lend.setLendInfo(borrowInfoApprovalVO.getLendInfo());//描述

        //平台预期收益
        //        月年化 = 年化 / 12
        BigDecimal monthRate = lend.getServiceRate().divide(new BigDecimal(12), 8, BigDecimal.ROUND_DOWN);
        //        平台收益 = 标的金额 * 月年化 * 期数
        BigDecimal expectAmount = lend.getAmount().multiply(monthRate).multiply(new BigDecimal(lend.getPeriod()));
        lend.setExpectAmount(expectAmount);

        //实际收益
        lend.setRealAmount(new BigDecimal(0));
        //状态
        lend.setStatus(LendStatusEnum.INVEST_RUN.getStatus());
        //审核时间
        lend.setCheckTime(LocalDateTime.now());
        //审核人
        lend.setCheckAdminId(1L);

        baseMapper.insert(lend);
    }

    @Override
    public Map<String, Object> getLendDetail(Long id) {
        HashMap<String, Object> map1 = new HashMap<>();
        Lend lend = baseMapper.selectById(id);
        LendVO lendVO = new LendVO();
        HashMap<String, Object> map = new HashMap<>();
        BeanUtils.copyProperties(lend,lendVO);
        lendVO.setParam(map);
        lendVO.getParam().put("returnMethod", dictService.getNameByDictCodeAndValue("returnMethod", lend.getReturnMethod()));
        lendVO.getParam().put("status", LendStatusEnum.getMsgByStatus(lend.getStatus()));
        map1.put("lend", lendVO);
        map1.put("borrower", borrowerService.showBorrowDetailByUserId(id));
        return map1;
    }

    @Override
    public BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, Integer totalmonth, Integer returnMethod) {

        BigDecimal interestCount;
        //计算总利息
        if (returnMethod.intValue() == ReturnMethodEnum.ONE.getStatus()) {
            interestCount = Amount1Helper.getInterestCount(invest, yearRate, totalmonth);
        } else if (returnMethod.intValue() == ReturnMethodEnum.TOW.getStatus()) {
            interestCount = Amount2Helper.getInterestCount(invest, yearRate, totalmonth);
        } else if(returnMethod.intValue() == ReturnMethodEnum.THREE.getStatus()) {
            interestCount = Amount3Helper.getInterestCount(invest, yearRate, totalmonth);
        } else {
            interestCount = Amount4Helper.getInterestCount(invest, yearRate, totalmonth);
        }
        return interestCount;
    }
}
