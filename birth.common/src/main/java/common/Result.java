package common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by xiangDong.qu on 15/10/26.
 * 返回结果
 */
@EqualsAndHashCode
public class Result<T> {
    /**
     * 消息码
     */
    private String code;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 是否成功 标志
     */
    private boolean success;

    public Result() {
    }

    public Result(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static <T> Result<T> wrapSuccessfulResult(T data) {
        Result<T> result = new Result();
        result.data = data;
        result.success = true;
        return result;
    }

/*    public static <T> Result<T> wrapErrorResult(LegendError error) {
        Result<T> result = new Result();
        result.success = false;
        result.code = error.getCode();
        result.errorMsg = error.getMessage();
        return result;
    }*/
}
