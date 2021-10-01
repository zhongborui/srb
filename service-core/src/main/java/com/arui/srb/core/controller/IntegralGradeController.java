package com.arui.srb.core.controller;


import com.arui.srb.core.pojo.entity.IntegralGrade;
import com.arui.srb.core.service.IntegralGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */

@RestController
@RequestMapping("/integralGrade")
public class IntegralGradeController {
}

//@RestController
//@RequestMapping("/api/core/integralGrade")
//public class IntegralGradeController {
//    @Resource
//    private IntegralGradeService integralGradeService;
//
//    @GetMapping("/list")
//    public List<IntegralGrade> list(){
//        return integralGradeService.list();
//    }
//}

