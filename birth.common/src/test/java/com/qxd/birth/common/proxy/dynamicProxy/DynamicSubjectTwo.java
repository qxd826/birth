package com.qxd.birth.common.proxy.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xiangdong.qu on 17/2/16 17:49.
 */
public class DynamicSubjectTwo<T> implements InvocationHandler {

    //对真实对象的引用
    private T sub;

    public DynamicSubjectTwo(T obj) {
        this.sub = obj;
    }

    public static <T> T factory(T obj) {
        Class<?> classType = obj.getClass();
        return (T) Proxy.newProxyInstance(classType.getClassLoader(),
                classType.getInterfaces(), new DynamicSubjectTwo(obj));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before Calling DynamicSubjectTwo: <<============================================>>");
        System.out.println("Proxy Name: " + proxy.getClass());
        System.out.println("Method Name: " + method.getName());
        System.out.println("Args[] Name: " + (null == args ? null : args.toString()));

        //通过动态代理类，我们可以在执行真实对象的方法前后加入自己的一些额外方法
        //TODO: 17/2/16  加入自己的方法

        //通过反射来调用方法
        return method.invoke(sub, args);
    }
}
