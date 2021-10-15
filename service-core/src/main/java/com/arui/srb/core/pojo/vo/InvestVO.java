package com.arui.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "投标信息")
public class InvestVO {


    @ApiModelProperty(value = "标的id")
    private Long lendId;

    @ApiModelProperty(value = "投资金额")
    private String investAmount;

    @ApiModelProperty(value = "投资人id")
    private Long investUserId;

    @ApiModelProperty(value = "投资人姓名")
    private String investName;
}