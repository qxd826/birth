package com.qxd.birth.biz.test.providrConsumer;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiangdong.qu on 17/4/2 16:12.
 */
@Slf4j
public class ConsumerThread implements Runnable {

    private MyFactoryBean myFactoryBean;

    public ConsumerThread(MyFactoryBean myFactoryBean) {
        this.myFactoryBean = myFactoryBean;
    }

    @Override
    public void run() {
        while (true) {
            log.info("消费者:" + Thread.currentThread());
            try {
                Thread.sleep(1000);
                myFactoryBean.consume();
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }
}
