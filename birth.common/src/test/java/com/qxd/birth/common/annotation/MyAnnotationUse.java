package com.qxd.birth.common.annotation;

/**
 * Created by xiangdong.qu on 17/2/20 15:44.
 */
@MyAnnotation
@SuppressWarnings({"unchecked", "deprecation"}) //如果注解一个类去压制一种警告，再注解类中的方法取压制另一种警告，则方法中会同时压制这两种警告。
public class MyAnnotationUse {

    @MyAnnotation
    public String value;

    @MyAnnotation(value = "qxdone")
    public String valueOne;

    @MyAnnotation(home = "yuhang")
    public String home;

    @SuppressWarnings("unchecked")
    @MyAnnotation
    public void method() {
        System.out.println("test annotation");
    }
}
