package com.qxd.birth.common.common;

import com.qxd.birth.common.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yuchengdu on 16/9/12.
 */
public abstract class BizTemplate<T> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 业务层执行时长的默认阀值（ms）
     */
    public static final long THRESHOLD = 200L;

    protected BizTemplate() {
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
        logger.error("BIZ error while execute:{}", e);
    }

    public T execute() {
        try {
            checkParams();
        } catch (IllegalArgumentException e) {
            logger.debug("check param failed:{}", e);
            throw e;
        }
        long start = System.currentTimeMillis();
        try {
            T result = process();
            onSuccess();
            return result;
        } catch (BizException biz) {
            onError(biz);
            throw biz;
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
