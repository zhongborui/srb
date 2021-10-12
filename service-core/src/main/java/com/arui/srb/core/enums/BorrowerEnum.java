package com.arui.srb.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ...
 */
@AllArgsConstructor
@Getter
public enum BorrowerEnum {

    UN_AUTHENTICATE(0, "未认证"),
    AUTHENTICATING(1, "认证中"),
    AUTHENTICATED(2, "认证通过"),
    AUTHENTICATE_ERROR(-1, "认证失败"),
    ;

    private Integer status;
    private String message;
}
