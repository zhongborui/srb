package com.arui.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ...
 */
@Data
@ApiModel(value = "用户登录对象VO")
public class LoginVO {

    @ApiModelProperty(value = "用户类型1投资人，2借款人")
    private Integer userType;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;
}

