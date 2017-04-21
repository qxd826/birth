package com.qxd.birth.biz.test.providrConsumer;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiangdong.qu on 17/4/2 16:11.
 */
@Slf4j
public class ProviderThread implements Runnable {

    private MyFactoryBean myFactoryBean;

    public ProviderThread(MyFactoryBean myFactoryBean) {
        this.myFactoryBean = myFactoryBean;
    }

    @Override
    public void run() {
        while (true) {
            log.info("生产者:" + Thread.currentThread());
            try {
                Thread.sleep(1000);
                myFactoryBean.provide();
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }
}
