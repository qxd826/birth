package com.qxd.birth.common.proxy.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by xiangdong.qu on 17/2/16 15:37.
 * <p>
 * 该代理类的内部属性是Object类型,实际使用的时候通过该类的构造方法传递进来一个对象
 * 该类实现了invoke()方法,该方法中的method.invoke()其实就是调用被代理对象的将要执行的方法
 * 方法参数sub表示该方法从属于sub
 * 通过动态代理类，我们可以在执行真实对象的方法前后加入自己的一些额外方法
 */
public class DynamicSubjectOne implements InvocationHandler {

    //对真实对象的引用
    private Object sub;

    public DynamicSubjectOne(Object obj) {
        this.sub = obj;
    }

    public void setSub(Object obj) {
        this.sub = obj;
    }

    /**
     * 在实际使用时
     * 第一个参数obj一般是指代理类
     * method是被代理的方法,如上例中的sing()
     * args为该方法的参数数(无参时设置为null)
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before Calling DynamicSubjectOne: <<============================================>>");
        System.out.println("Proxy Name: " + proxy.getClass());
        System.out.println("Method Name: " + method.getName());
        System.out.println("Args[] Name: " + (null == args ? null : args.toString()));

        //通过动态代理类，我们可以在执行真实对象的方法前后加入自己的一些额外方法
        //TODO: 17/2/16  加入自己的方法

        //通过反射来调用方法
        return method.invoke(sub, args);
    }
}
