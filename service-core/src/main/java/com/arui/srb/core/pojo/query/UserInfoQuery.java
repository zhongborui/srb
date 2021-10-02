package com.arui.srb.core.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ...
 */
@Data
@ApiModel(value = "会员查询对象", description = "会员信息查询对象")
public class UserInfoQuery {

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value ="用户状态")
    private Integer status;
}
