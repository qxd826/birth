package com.qxd.birth.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xiangdong.qu on 16/9/8 10:40.
 */
@Slf4j
public class ImageUtils {
    //连接超时
    private final static int connTimeOut = 3 * 1000;
    //读取超时
    private final static int readTimeOut = 6 * 10000;

    public static String getImageBase64Str(String imageUrl) {
        if (StringUtils.isBlank(imageUrl)) {
            return null;
        }
        InputStream inStream = null;
        try {
            URL url = new URL(imageUrl);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为3秒
            conn.setConnectTimeout(connTimeOut);
            //设置读取超时为6秒
            conn.setReadTimeout(readTimeOut);
            //通过输入流获取图片数据
            inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            //base64编码
            BASE64Encoder base64Encoder = new BASE64Encoder();
            return base64Encoder.encode(data);
        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                if (null != inStream) {
                    inStream.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return null;
    }

    public static byte[] readInputStream(InputStream inStream) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] resultByteArray = null;
        try {
            //创建一个Buffer字节串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = inStream.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            //关闭输入流
            inStream.close();
            //把outStream里的数据写入内存
            resultByteArray = outStream.toByteArray();
        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                if (null != inStream) {
                    inStream.close();
                }
                if (null != outStream) {
                    outStream.close();
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return resultByteArray;
    }
}
