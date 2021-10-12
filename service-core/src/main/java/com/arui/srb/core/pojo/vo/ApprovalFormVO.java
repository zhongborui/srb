package com.arui.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ...
 */
@Data
@ApiModel(value = "审核借款人积分对象VO")
public class ApprovalFormVO {

    @ApiModelProperty(value = "借款人id")
    private Long borrowerId;

    @ApiModelProperty(value = "借款人状态")
    private Integer status;

    @ApiModelProperty(value = "借款人内容")
    private String content;

    @ApiModelProperty(value = "借款人基本积分")
    private Integer infoIntegral;

    @ApiModelProperty(value = "借款人身份证信息是否正确")
    private Boolean isIdCardOk;

    @ApiModelProperty(value = "车辆信息是否正确")
    private Boolean isHouseOk;

    @ApiModelProperty(value = "房产信息是否正确")
    private Boolean isCarOk;

}
