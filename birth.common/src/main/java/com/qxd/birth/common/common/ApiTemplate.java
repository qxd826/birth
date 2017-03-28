package com.qxd.birth.common.common;

import com.qxd.birth.common.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yuchengdu on 16/8/26.
 */
public abstract class ApiTemplate<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 业务层执行时长的默认阀值（ms）
     */
    public static final long THRESHOLD = 350L;

    protected ApiTemplate() {
    }


    /**
     * 参数合法性检查 IllegalArgumentException
     */
    protected abstract void checkParams() throws IllegalArgumentException;

    /**
     * 主逻辑入口 抛出BizException类型的异常 在execute方法中进行处理
     *
     * @return
     *
     * @throws BizException
     */
    protected abstract T process() throws BizException;

    /**
     * 后置处理器
     */
    protected void afterProcess() {
    }

    protected void onSuccess() {
    }

    protected void onError(Throwable e) {
        logger.error("API error while execute:{}", e);
    }

    public Result<T> execute() {
        try {
            checkParams();
        } catch (IllegalArgumentException e) {
            logger.debug("check param failed:{}", e);
            return Result.wrapErrorResult("", e.getMessage());
        }
        long start = System.currentTimeMillis();
        try {
            T result = process();
            onSuccess();
            return Result.wrapSuccessfulResult(result);
        } catch (BizException biz) {
            onError(biz);
            return Result.wrapErrorResult(biz.getCode(), biz.getMessage());
        } catch (Exception exp) {
            onError(exp);
            return Result.wrapErrorResult("", "系统异常,请稍后重试");
        } catch (Throwable e) {
            onError(e);
            throw e;
        } finally {
            afterProcess();
            long totalTime = System.currentTimeMillis() - start;
            if (totalTime > THRESHOLD) {
                logger.warn("This method used too long time please check and optimize it:{}ms", totalTime);
            }
        }
    }


}
