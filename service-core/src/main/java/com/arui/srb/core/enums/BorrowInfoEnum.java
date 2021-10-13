package com.arui.srb.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ...
 */
@AllArgsConstructor
@Getter
public enum  BorrowInfoEnum {

    UN_COMMIT(0, "未提交"),
    APPROVING(1, "审核中"),
    APPROVED(2, "审核通过"),
    APPROVED_FAIL(-1, "审核不通过")
    ;

    private Integer status;
    private String message;
}
