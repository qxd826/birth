package com.qxd.birth.common.proxy;

import com.qxd.birth.common.proxy.dynamicProxy.DynamicSubjectOne;
import com.qxd.birth.common.proxy.dynamicProxy.DynamicSubjectTwo;
import com.qxd.birth.common.proxy.staticProxy.SubjectProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by xiangdong.qu on 17/2/16 15:12.
 */
@Slf4j
public class ProxyTestMain {

    /**
     * 测试静态代理
     */
    @Test
    public void testStaticProxy() {
        Subject subject = new SubjectProxy();
        subject.sing();
    }

    /**
     * 动态代理测试 分解测试
     */
    @Test
    public void testDynamicProxyOne() {
        //实现类One
        SubjectImplOne subjectImplOne = new SubjectImplOne();
        //实现类Two
        SubjectImplTwo subjectImplTwo = new SubjectImplTwo();

        //代理类
        DynamicSubjectOne dynamicSubjectOne = new DynamicSubjectOne(subjectImplOne);

        // 生成代理
        // 动态生成一个类（实现了指定的接口）,生成类的对象,转换成接口类型
        Subject subject = (Subject) Proxy.newProxyInstance(
                dynamicSubjectOne.getClass().getClassLoader(),
                subjectImplOne.getClass().getInterfaces(),
                dynamicSubjectOne);
        subject.sing();
        log.info(subject.sing("i am sing"));

        //代理类(替换代理对象)
        dynamicSubjectOne.setSub(subjectImplTwo);
        //动态生成一个类（实现了指定的接口）,生成类的对象,转换成接口类型
        subject = (Subject) Proxy.newProxyInstance(
                dynamicSubjectOne.getClass().getClassLoader(),
                subjectImplTwo.getClass().getInterfaces(),//声明实现的接口,获取该类实现的接口
                dynamicSubjectOne);
        subject.sing();
        log.info(subject.sing("i am sing"));
    }

    /**
     *
     */
    @Test
    public void testDynamicProxyTwo() {
        //实现类One
        SubjectImplOne subjectImplOne = new SubjectImplOne();
        Subject subject = DynamicSubjectTwo.factory(subjectImplOne);
        subject.sing();
        log.info(subject.sing("xxyyzz"));
    }
}
