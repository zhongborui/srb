package com.arui.srb.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ...
 */
@AllArgsConstructor
@Getter
public enum ReturnMethodEnum {

    ONE(1, "等额本息"),
    TOW(2, "等额本金"),
    THREE(3, "每月还本一次"),
    FOUR(4, "一次还本")
    ;

    private Integer status;
    private String message;

    public static String getMessageByStatus(Integer status){
        ReturnMethodEnum[] values = ReturnMethodEnum.values();
        for (ReturnMethodEnum value : values) {
            if (value.status.equals(status)){
                return value.message;
            }
        }

        return "";
    }
}
