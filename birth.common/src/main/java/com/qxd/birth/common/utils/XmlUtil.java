package com.qxd.birth.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by xiangqong.qu on 16/9/30 16:38.
 */
@Slf4j
public class XmlUtil {

    //默认编码格式
    private static final String encoding = "UTF-8";

    /**
     * @param root  待转换数据
     * @param types 待转换数据class
     *
     * @return
     */
    public static String dataToXml(Object root, Class<?> types) {
        return dataToXml(root, types, encoding);
    }

    /**
     * @param root     待转换数据
     * @param types    待转换数据class
     * @param encoding 编码格式
     *
     * @return
     */
    public static String dataToXml(Object root, Class<?> types, String encoding) {
        try {
            //初始化工厂 和 要生成的类
            JaxbUtil jaxbUtilRequest = new JaxbUtil(types, JaxbUtil.CollectionWrapper.class);
            String tempXml = jaxbUtilRequest.toXml(root, encoding);
            return jaxbUtilRequest.xmlFormat(tempXml, encoding);
        } catch (Exception e) {
            log.error("xml format error.", e);
        }
        return null;
    }

    /**
     * @param xml  待转换xml数据
     * @param type 输出类型class
     * @param <T>  输出数据
     *
     * @return
     */
    public static <T> T dataFromXml(String xml, Class<T> type) {
        try {
            JaxbUtil jaxbUtilResponse = new JaxbUtil(type, JaxbUtil.CollectionWrapper.class);
            return jaxbUtilResponse.fromXml(xml);
        } catch (Exception e) {
            log.error("xml to data error.", e);
        }
        return null;
    }
}
