package com.arui.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 借款人上传资源表
 * </p>
 *
 * @author arui
 * @since 2021-09-22
 */
@Data
@ApiModel(value="BorrowerAttach对象VO", description="借款人上传资源表")
public class BorrowerAttachVO{

    @ApiModelProperty(value = "图片类型（idCard1：身份证正面，idCard2：身份证反面，house：房产证，car：车）")
    private String imageType;

    @ApiModelProperty(value = "图片路径")
    private String imageUrl;

    @ApiModelProperty(value = "图片名称")
    private String imageName;
}
