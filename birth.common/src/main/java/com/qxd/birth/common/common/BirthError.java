package com.qxd.birth.common.common;

/**
 * Created by xiangDong.qu on 15/10/26.
 */
public enum BirthError {
    PARAM_ERROR("10000", "参数错误"),
    COMMON_ERROR("10001", "应用程序错误");
    private String code;
    private String message;

    private BirthError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
