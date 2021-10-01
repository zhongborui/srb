package com.arui.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ...
 */
@Data
@ApiModel(value = "用户信息对象VO")
public class UserInfoVO {

    @ApiModelProperty(value = "用户类型1投资人，2借款人")
    private Integer userType;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "头像")
    private String headImg;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "JWT访问令牌")
    private String token;

}
