package com.arui.srb.core.service.impl;

import com.arui.srb.core.enums.BorrowerEnum;
import com.arui.srb.core.enums.UserBindEnum;
import com.arui.srb.core.mapper.*;
import com.arui.srb.core.pojo.entity.*;
import com.arui.srb.core.pojo.vo.ApprovalFormVO;
import com.arui.srb.core.pojo.vo.BorrowerAttachVO;
import com.arui.srb.core.pojo.vo.BorrowerDetailVO;
import com.arui.srb.core.pojo.vo.BorrowerVO;
import com.arui.srb.core.service.BorrowerService;
import com.arui.srb.core.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 借款人 服务实现类
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Service
public class BorrowerServiceImpl extends ServiceImpl<BorrowerMapper, Borrower> implements BorrowerService {

    @Resource
    private BorrowerMapper borrowerMapper;

    @Resource
    private BorrowerAttachMapper borrowerAttachMapper;

    @Resource
    private UserBindMapper userBindMapper;

    @Resource
    private DictService dictService;

    @Resource
    private UserIntegralMapper userIntegralMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public void saveBorrower(BorrowerVO borrowerVO, Long userId) {
        // 保存到borrower表
        Borrower borrower = new Borrower();
        BeanUtils.copyProperties(borrowerVO, borrower);
        borrower.setUserId(userId);
        borrower.setStatus(BorrowerEnum.AUTHENTICATING.getStatus());
        QueryWrapper<UserBind> userBindQueryWrapper = new QueryWrapper<>();
        UserBind userBind = userBindMapper.selectOne(userBindQueryWrapper.eq("user_id", userId));
        borrower.setIdCard(userBind.getIdCard());
        borrower.setName(userBind.getName());
        borrower.setMobile(userBind.getMobile());
        borrowerMapper.insert(borrower);

        // 保存到borrower_attach表
        List<BorrowerAttachVO> borrowerAttachVOList = borrowerVO.getBorrowerAttachList();
        for (BorrowerAttachVO borrowerAttachVO : borrowerAttachVOList) {
            BorrowerAttach borrowerAttach = new BorrowerAttach();
            BeanUtils.copyProperties(borrowerAttachVO, borrowerAttach);
            borrowerAttach.setBorrowerId(userId);
            borrowerAttachMapper.insert(borrowerAttach);
        }
    }

    @Override
    public Integer getBorrowerStatusByUserId(Long userId) {
        QueryWrapper<Borrower> borrowerQueryWrapper = new QueryWrapper<>();
        borrowerQueryWrapper.select("status").eq("user_id", userId);
        List<Object> objects = baseMapper.selectObjs(borrowerQueryWrapper);
        return (Integer)objects.get(0);
    }

    @Override
    public IPage<Borrower> listPage(Page<Borrower> borrowerPage, String keyword) {
        Page<Borrower> pageModel = null;
        if (keyword != null && ! keyword.equals("")){
            QueryWrapper<Borrower> borrowerQueryWrapper = new QueryWrapper<>();
            borrowerQueryWrapper.like("name", keyword)
                    .or().like("mobile", keyword)
                    .or().like("id_card", keyword);
             pageModel = borrowerMapper.selectPage(borrowerPage, borrowerQueryWrapper);
        }else {
            pageModel = borrowerMapper.selectPage(borrowerPage, null);
        }
        return pageModel;

    }

    @Override
    public BorrowerDetailVO showBorrowDetailByUserId(Long id) {
        BorrowerDetailVO borrowerDetailVO = new BorrowerDetailVO();
        // 查询borrower表信息
        QueryWrapper<Borrower> borrowerQueryWrapper = new QueryWrapper<>();
        borrowerQueryWrapper.eq("user_id", id);
        Borrower borrower = borrowerMapper.selectOne(borrowerQueryWrapper);
        BeanUtils.copyProperties(borrower, borrowerDetailVO);
        borrowerDetailVO.setStatus(BorrowerEnum.getMessageByStatus(borrower.getStatus()));
        borrowerDetailVO.setSex(borrower.getSex().equals(1)? "男" : "女");
        borrowerDetailVO.setMarry(borrower.getMarry().equals(1) ? "已婚": "未婚" );

        String education = dictService.getNameByDictCodeAndValue("education", borrower.getEducation());
        String industry = dictService.getNameByDictCodeAndValue("industry", borrower.getIndustry());
        String returnSource = dictService.getNameByDictCodeAndValue("returnSource", borrower.getReturnSource());
        String income = dictService.getNameByDictCodeAndValue("income", borrower.getIncome());
        String relation = dictService.getNameByDictCodeAndValue("relation", borrower.getContactsRelation());

        borrowerDetailVO.setEducation(education);
        borrowerDetailVO.setIndustry(industry);
        borrowerDetailVO.setReturnSource(returnSource);
        borrowerDetailVO.setContactsRelation(relation);
        borrowerDetailVO.setIncome(income);

        // 查询borrowerAttach表信息
        QueryWrapper<BorrowerAttach> borrowerAttachQueryWrapper = new QueryWrapper<>();
        borrowerAttachQueryWrapper.eq("borrower_id", id);
        List<BorrowerAttach> borrowerAttachList = borrowerAttachMapper.selectList(borrowerAttachQueryWrapper);

        // 流式编程
        List<BorrowerAttachVO> borrowerAttachVOS = borrowerAttachList.stream().map(borrowerAttach -> {
            BorrowerAttachVO borrowerAttachVO = new BorrowerAttachVO();
            BeanUtils.copyProperties(borrowerAttach, borrowerAttachVO);
            return borrowerAttachVO;
        }).collect(Collectors.toList());

        borrowerDetailVO.setBorrowerAttachVOList(borrowerAttachVOS);
        return borrowerDetailVO;
    }

    @Override
    public void approval(ApprovalFormVO approvalFormVO) {
        Long borrowerId = approvalFormVO.getBorrowerId();
        QueryWrapper<Borrower> borrowerQueryWrapper = new QueryWrapper<>();
        borrowerQueryWrapper.eq("user_id", borrowerId);
        Borrower borrower = baseMapper.selectOne(borrowerQueryWrapper);

        UserInfo userInfo = userInfoMapper.selectById(borrowerId);

        // 判断用户是否通过认证
        if (approvalFormVO.getStatus() == -1){
            // 认证失败
            borrower.setStatus(BorrowerEnum.AUTHENTICATE_ERROR.getStatus());
            baseMapper.updateById(borrower);
            userInfo.setBorrowAuthStatus(BorrowerEnum.AUTHENTICATE_ERROR.getStatus());
            userInfoMapper.insert(userInfo);
            return;
        }
        // 认证成功
        Integer sumIntegral = approvalFormVO.getInfoIntegral()
                + (approvalFormVO.getIsCarOk()?60:0)
                + (approvalFormVO.getIsIdCardOk() ? 30 : 0 )
                + (approvalFormVO.getIsHouseOk() ? 100 : 0);

        // borrower 表修改认证状态
        borrower.setStatus(BorrowerEnum.AUTHENTICATED.getStatus());
        baseMapper.updateById(borrower);

        // userInfo 表修改状态和积分
        userInfo.setBorrowAuthStatus(BorrowerEnum.AUTHENTICATED.getStatus());
        userInfo.setIntegral(sumIntegral);
        userInfoMapper.updateById(userInfo);

        // user_integral 插入用户积分
        UserIntegral userIntegral = new UserIntegral();
        userIntegral.setUserId(borrowerId);
        userIntegral.setIntegral(sumIntegral);
        userIntegral.setContent(approvalFormVO.getContent());
        userIntegralMapper.insert(userIntegral);
    }
}
