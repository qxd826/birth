package com.qxd.birth.common.exception;

/**
 * 业务场景: 业务异常类
 * 修改记录:
 */
public class BizException extends RuntimeException {
    /**
     * 错误码
     */
    private String code;
    /**
     * 错误信息
     */
    private String message;

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
        this.message = message;
    }

    public BizException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
