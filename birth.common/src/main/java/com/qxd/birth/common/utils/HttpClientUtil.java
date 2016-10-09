package com.qxd.birth.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by xiangqong.qu on 16/9/30 16:26.
 */

/**
 * 注意:
 * base64过的数据 经过网络传输时 最好进行URLEncoder.encode解决 base64数据经过网络传输后 空格的问题
 */
@Slf4j
public class HttpClientUtil {

    //默认编码格式
    private static final String encoding = "UTF-8";

    /**
     * @param data xml对象
     * @param url  请求链接
     *
     * @return String
     *
     * @throws Exception
     */
    public static String sendXmlData(Object data, String url) throws Exception {
        String xmlStr = XmlUtil.dataToXml(data, data.getClass());
        return sendXmlData(xmlStr, url);
    }

    /**
     * @param requestData  请求数据
     * @param responseType 返回数据类型
     * @param url          请求链接
     * @param <T>          返回数据类型
     * @param <D>          请求数据data
     *
     * @return
     *
     * @throws Exception
     */
    public static <T, D> T sendXmlData(D requestData, Class<T> responseType, String url) throws Exception {
        String xmlStr = XmlUtil.dataToXml(requestData, requestData.getClass());
        //请求前加密
        String deXmlStr = sendXmlData(xmlStr, url, encoding);
        if (StringUtils.isBlank(deXmlStr)) {
            return null;
        }
        //解密
        T t = XmlUtil.dataFromXml(deXmlStr, responseType);
        return t;
    }


    /**
     * post 请求 默认编码utf-8
     *
     * @param xmlUndwrtData xml格式数据
     * @param url           请求链接
     *
     * @return
     *
     * @throws Exception
     */
    public static String sendXmlData(String xmlUndwrtData, String url) throws Exception {
        return sendXmlData(xmlUndwrtData, url, encoding);
    }

    /**
     * post 请求
     *
     * @param xmlUndwrtData xml格式数据
     * @param url           请求链接
     * @param encoding      编码格式
     *
     * @return
     *
     * @throws Exception
     */
    public static String sendXmlData(String xmlUndwrtData, String url, String encoding) throws Exception {
        byte[] bs = null;
        int intConnectTimeout = 6000;
        int intReadTimeout = 60000;
        HttpClient httpClient = new HttpClient();
        HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
        managerParams.setConnectionTimeout(intConnectTimeout);
        managerParams.setSoTimeout(intReadTimeout);
        PostMethod postMethod = new PostMethod(url);
        try {
            String contentType = "text/xml;charset=" + encoding;
            xmlUndwrtData = new String(xmlUndwrtData.getBytes(encoding), encoding);
            postMethod.setRequestEntity(new StringRequestEntity(xmlUndwrtData, contentType, encoding));
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                throw new IllegalStateException("Method failed: " + postMethod.getStatusLine());
            }
            bs = postMethod.getResponseBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            postMethod.releaseConnection();
        }
        return new String(bs, encoding);
    }

    /**
     * http post 请求
     *
     * @param data     请求数据
     * @param url      请求链接
     * @param encoding 编码格式
     *
     * @return
     *
     * @throws Exception
     */
    public static String sendData(Object data, String url, String encoding) throws Exception {
        Map<String, Object> beanMap = MBeanUtil.bean2Map(data);
        NameValuePair[] param = new NameValuePair[beanMap.size()];
        int i = 0;
        for (Map.Entry<String, Object> entity : beanMap.entrySet()) {
            NameValuePair nameValuePair = new NameValuePair();
            nameValuePair.setName(entity.getKey());
            nameValuePair.setValue(entity.getValue().toString());
            if (i < beanMap.size()) {
                param[i++] = nameValuePair;
            }
        }
        if (i != beanMap.size()) {
            log.error("httpclient post [参数错误]");
            return null;
        }
        StringBuffer resultBuffer = new StringBuffer();
        try {
            String contentType = "application/x-www-form-urlencoded;charset=" + encoding;
            HttpClient client = new HttpClient();
            PostMethod postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", contentType);
            postMethod.setRequestBody(param);
            postMethod.releaseConnection();
            client.executeMethod(postMethod);
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), "UTF-8"));
            while ((line = bufferedReader.readLine()) != null) {
                resultBuffer.append(line);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return resultBuffer.toString();
    }
}
