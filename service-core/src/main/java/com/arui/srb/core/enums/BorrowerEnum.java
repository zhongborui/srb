package com.arui.srb.core.enums;

import io.swagger.models.auth.In;
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

    public static String getMessageByStatus(Integer status){
        BorrowerEnum[] objs = BorrowerEnum.values();
        for (BorrowerEnum obj : objs) {
            if (obj.getStatus().equals(status)){
                return obj.getMessage();
            }
        }
        return "";
    }
}
