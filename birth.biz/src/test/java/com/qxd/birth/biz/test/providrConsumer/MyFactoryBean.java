package com.qxd.birth.biz.test.providrConsumer;

import lombok.extern.slf4j.Slf4j;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by xiangdong.qu on 17/4/2 16:13.
 */
@Slf4j
public class MyFactoryBean {
    private static int size = 10;
    public static Queue<Integer> integerQueue = new PriorityQueue<>(size);

    private Object object = new Object();

    private Integer product = new Integer(1);


    public void provide() throws Exception {
        synchronized (object) {
            if (integerQueue.size() >= size) {
                log.info("" + Thread.currentThread() + "生产完了等待消费");
                object.wait();
            } else {
                integerQueue.add(++product);
                log.info("" + Thread.currentThread() + "生产了:" + product);
            }
            object.notify();
        }
    }

    public void consume() throws Exception {
        synchronized (object) {
            Integer s = integerQueue.poll();
            log.info("" + Thread.currentThread() + "消费了:" + s);
            if (s == null) {
                log.info("" + Thread.currentThread() + "消费完了等待生产");
                object.wait();
            }
            object.notify();
        }
    }
}
