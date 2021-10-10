package com.arui.srb.core.enums;

import lombok.*;

/**
 * @author ...
 */
@ToString
@AllArgsConstructor
@Getter
public enum UserBindEnum {
    NO_Bind(0, "未绑定"),
    OK_Bind(1, "绑定成功")
    ;

    private Integer code;
    private String message;
}
