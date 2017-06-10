package com.qxd.birth.biz.test.providrConsumer;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiangdong.qu on 17/4/2 18:20.
 */
public class Test {
    private int queueSize = 3;
    private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public static void main(String[] args) {
        Test test = new Test();
        Producer producer = test.new Producer();
        Producer producerTwo = test.new Producer();
        Consumer consumer = test.new Consumer();
        Consumer consumerTwo = test.new Consumer();
        Consumer consumer3 = test.new Consumer();
        Consumer consumer4 = test.new Consumer();
        Consumer consumer5 = test.new Consumer();
        Consumer consumer6 = test.new Consumer();

        producer.start();
        consumer.start();
        producerTwo.start();
        consumerTwo.start();
        consumer3.start();
        consumer4.start();
        consumer5.start();
        consumer6.start();
    }

    class Consumer extends Thread {

        @Override
        public void run() {
            consume();
        }

        private void consume() {
            while (true) {
                lock.lock();
                try {
                    Thread.sleep(1000);
                    while (queue.size() == 0) {
                        try {
                            System.out.println("队列空，等待数据");
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();                //每次移走队首元素
                    notFull.signal();            //每次唤醒读线程
                    System.out.println(Thread.currentThread() + "从队列取走一个元素，队列剩余" + queue.size() + "个元素");
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class Producer extends Thread {

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            while (true) {
                lock.lock();
                try {
                    Thread.sleep(1000);
                    while (queue.size() == queueSize) {
                        try {
                            System.out.println("队列满，等待有空余空间");
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(1);        //每次插入一个元素
                    notEmpty.signal();     //每次唤醒写线程
                    System.out.println(Thread.currentThread() + "向队列取中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
                } catch (Exception e) {

                } finally {
                    lock.unlock();
                }
            }
        }
    }
}

