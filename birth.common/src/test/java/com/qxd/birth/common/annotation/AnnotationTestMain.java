package com.qxd.birth.common.annotation;

import com.qxd.birth.common.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by xiangdong.qu on 17/2/20 15:48.
 */
@Slf4j
public class AnnotationTestMain extends BaseTest {

    @Autowired
    private MyAnnotationUse myAnnotationUseOne;

    @Test
    public void testOne() {
        MyAnnotationUse myAnnotationUse = new MyAnnotationUse();

        //没有将注解的值对应到对应的属性
        log.info("home:", myAnnotationUse.home);
        log.info("value:", myAnnotationUse.value);
        log.info("valueone:", myAnnotationUse.valueOne);
        myAnnotationUse.method();
        log.info("myAnnotationUseOne:{}", myAnnotationUseOne);
        myAnnotationUseOne.method();

        log.info("获取类的注解:MyAnnotationUse.class");
        log.info("-----------");
        Annotation[] classAnnotations = MyAnnotationUse.class.getDeclaredAnnotations();
        for (Annotation annotation : classAnnotations) {
            log.info(annotation.annotationType().getName());
        }
        log.info("-----------");

        //获取类的属性
        log.info("获取类的属性:MyAnnotationUse.class");
        log.info("-----------");
        Field[] fields = MyAnnotationUse.class.getDeclaredFields();
        for (Field field : fields) {
            log.info(field.getName() + ":");
            //获取属性的公开的注解
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                log.info(annotation.annotationType().getName());
                Field[] fieldsTemp = annotation.getClass().getDeclaredFields();
                for (Field fieldTemp : fieldsTemp) {

                }
            }
        }
        log.info("-----------");
    }
}
