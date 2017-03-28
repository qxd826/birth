package com.qxd.birth.common.annotation;

/**
 * Created by xiangdong.qu on 17/2/20 15:38.
 */

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 预设上编译程序会将Annotation信息留在.class档案中，但不被虚拟机读取，而仅用于编译程序或工具程序运行时提供信息。
 * <p>
 * public enum RetentionPolicy
 * {
 * SOURCE,   //编译程序处理完Annotation信息后就完成任务,注解只会存在于源文件当中,编译器将其丢弃,不会把注解编译到class文件当中,
 * CLASS,    //编译程序将Annotation存储于class档中,缺省,即默认情况是这种行为
 * RUNTIME   //编译程序将Annotation存储于class档中,可由VM读入,可以通过反射的方式读取到
 * }
 */

/**
 * 定义注解可以使用的地方
 * package java.lang.annotation;
 * <p>
 * public enum ElementType
 * {
 * TYPE,   //适用class，interface，enum
 * FIELD, //适用field
 * METHOD,   //适用method
 * PARAMETER,   //适用method上之parameter
 * CONSTRUCTOR,    //适用constructor
 * LOCAL_VARIABLE,     //适用局部变量
 * ANNOTATION_TYPE,   //适用annotation型态
 * PACKAGE,   //使用package
 * }
 */

@Retention(RetentionPolicy.RUNTIME)
@Component   //标示该注解也有component注解的功能,spring scan可以扫描到该注解,并将该类加入到spring管理中
@Documented  //表示注解DocumentedAnnotation将会生成到文档里面去
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface MyAnnotation {
    /*
    * 不加（）就出错了
    * 属性名字叫作value时可以不加名字直接赋值
    * 属性名字不叫作value时给属性赋值必须显式指定名字
    * 可以通过default关键字设置默认值
    * */
    String value() default "qxd";

    String home() default "hangzhou";
}
