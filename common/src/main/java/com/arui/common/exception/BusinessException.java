package com.arui.common.exception;

import com.arui.common.result.ResponseEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author ...
 */
@Getter
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    /**
     *
     * @param responseEnum 接收枚举类型
     */
    public BusinessException(ResponseEnum responseEnum){
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    /**
     *
     * @param responseEnum 接收枚举类型
     * @param cause 原始异常对象
     */
    public BusinessException(ResponseEnum responseEnum, Throwable cause){
        super(cause);
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    /**
     *
     * @param message 错误消息
     */
    public BusinessException(String message) {
        this.message = message;
    }

    /**
     *
     * @param message 错误消息
     * @param code 错误码
     */
    public BusinessException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    /**
     *
     * @param message 错误消息
     * @param code 错误码
     * @param cause 原始异常对象
     */
    public BusinessException(String message, Integer code, Throwable cause) {
        super(cause);
        this.message = message;
        this.code = code;
    }
}
