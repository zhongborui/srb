package com.arui.srb.core.service.impl;

import com.arui.srb.core.enums.BorrowerEnum;
import com.arui.srb.core.mapper.BorrowerAttachMapper;
import com.arui.srb.core.mapper.UserBindMapper;
import com.arui.srb.core.pojo.entity.Borrower;
import com.arui.srb.core.mapper.BorrowerMapper;
import com.arui.srb.core.pojo.entity.BorrowerAttach;
import com.arui.srb.core.pojo.entity.UserBind;
import com.arui.srb.core.pojo.vo.BorrowerAttachVO;
import com.arui.srb.core.pojo.vo.BorrowerVO;
import com.arui.srb.core.service.BorrowerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
