package com.arui.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ...
 */
@ApiModel(value = "前台用户账户绑定对象")
@Data
public class UserBindVO {

    @ApiModelProperty("用户姓名")
    private String name;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("银行卡号")
    private String bankNo;

    @ApiModelProperty("银行类型")
    private String bankType;

    @ApiModelProperty("手机号")
    private String mobile;
}
