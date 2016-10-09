package com.qxd.birth.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

/**
 * Created by xiangdong.qu on 16/9/6 16:10.
 */
@Slf4j
public class JaxbUtil {
    // 多线程安全的Context.
    private JAXBContext jaxbContext;

    private final static String encode = "UTF-8";

    /**
     * @param types 所有需要序列化的Root对象的类型.
     */
    public JaxbUtil(Class<?>... types) throws Exception {
        try {
            jaxbContext = JAXBContext.newInstance(types);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Java Object->Xml.
     */
    public String toXml(Object root, String encoding) throws Exception {
        try {
            StringWriter writer = new StringWriter();
            createMarshaller(encoding).marshal(root, writer);
            String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
            String str1 = writer.toString().replaceAll("\n", "\t");
            //格式化
            return xmlFormat(str + str1, encoding);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Java Object->Xml.
     */
    public String toXml(Object root) throws Exception {
        return toXml(root, encode);
    }

    /**
     * Java Object->Xml, 特别支持对Root Element是Collection的情形.
     */
    @SuppressWarnings("unchecked")
    public String toXml(Collection root, String rootName, String encoding) throws Exception {
        try {
            CollectionWrapper wrapper = new CollectionWrapper();
            wrapper.collection = root;
            JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(
                    new QName(rootName), CollectionWrapper.class, wrapper);
            StringWriter writer = new StringWriter();
            createMarshaller(encoding).marshal(wrapperElement, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Xml->Java Object.
     */
    @SuppressWarnings("unchecked")
    public <T> T fromXml(String xml) throws Exception {
        try {
            StringReader reader = new StringReader(xml);
            return (T) createUnmarshaller().unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Xml->Java Object, 支持大小写敏感或不敏感.
     */
    @SuppressWarnings("unchecked")
    public <T> T fromXml(String xml, boolean caseSensitive) throws Exception {
        String fromXml = xml;
        if (!caseSensitive) {
            fromXml = xml.toLowerCase();
        }
        return fromXml(fromXml);
    }

    /**
     * 创建Marshaller, 设定encoding(可为Null).
     */
    private Marshaller createMarshaller(String encoding) throws Exception {
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            if (null != encoding) {
                marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            }
            //marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);

            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            return marshaller;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建UnMarshaller.
     */
    private Unmarshaller createUnmarshaller() throws Exception {
        try {
            return jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 封装Root Element 是 Collection的情况.
     */
    public static class CollectionWrapper {
        @SuppressWarnings("unchecked")
        @XmlAnyElement
        protected Collection collection;
    }

    /**
     * xml报文格式化
     *
     * @param xmlStr 待格式化xml字符串
     *
     * @return xml数据串
     *
     * @throws Exception
     */
    public static String xmlFormat(String xmlStr) throws Exception {
        return xmlFormat(xmlStr, encode);
    }


    /**
     * @param xmlStr   待格式化xml字符串
     * @param encoding 编码格式
     *
     * @return xml数据串
     *
     * @throws Exception
     */
    public static String xmlFormat(String xmlStr, String encoding) throws Exception {
        StringWriter out = null;
        try {
            SAXReader reader = new SAXReader();
            // System.out.println(reader);
            // 注释：创建一个串的字符输入流
            StringReader in = new StringReader(xmlStr);
            Document doc = reader.read(in);
            // System.out.println(doc.getRootElement());
            // 注释：创建输出格式
            OutputFormat formater = OutputFormat.createPrettyPrint();
            //formater=OutputFormat.createCompactFormat();
            // 注释：设置xml的输出编码
            formater.setEncoding(encoding);
            // 注释：创建输出(目标)
            out = new StringWriter();
            // 注释：创建输出流
            XMLWriter writer = new XMLWriter(out, formater);
            // 注释：输出格式化的串到目标中，执行后。格式化后的串保存在out中。
            writer.write(doc);
            writer.close();
            // 注释：返回我们格式化后的结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }
}
