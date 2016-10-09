package com.qxd.birth.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangqong.qu on 16/10/9 11:16.
 */
@Slf4j
public class MBeanUtil {

    /**
     * 将bean对象转化为Map对象 剔除checkValue参数
     *
     * @param javaBean Java 对象
     * @param <K>
     * @param <V>
     *
     * @return
     */
    public static <K, V> Map<K, V> bean2Map(Object javaBean) {
        Map<K, V> ret = new HashMap<K, V>();
        try {
            Method[] methods = javaBean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    Object value = method.invoke(javaBean, (Object[]) null);
                    if (value != null && !"checkValue".equals(field)) {
                        ret.put((K) field, (V) (null == value ? "" : value));
                    }
                }
            }
        } catch (Exception e) {
            log.error("bean2Map error", e);
        }
        return ret;
    }
}
