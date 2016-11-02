package com.qxd.birth.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangdong.qu on 16/9/8 10:40.
 */
@Slf4j
public class ImageUtil {
    //连接超时
    private final static int connTimeOut = 3 * 1000;
    //读取超时
    private final static int readTimeOut = 6 * 10000;

    /**
     * 将图片对象转为数组对象
     *
     * @param bufferedImage 图片对象
     * @param imageType     图片类别 jpg,png等
     *
     * @return
     */
    public static byte[] imageToByteArray(BufferedImage bufferedImage, String imageType) {
        if (null == bufferedImage || StringUtils.isBlank(imageType)) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, imageType, byteArrayOutputStream);
        } catch (Exception e) {
            log.error("图片转为byte数组失败", e);
            return null;
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 将图片数组转化为图片对象
     *
     * @param imageByteArray 图片数组
     *
     * @return
     */
    public static BufferedImage byteArrayToImage(byte[] imageByteArray) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteArray));
        } catch (Exception e) {
            log.error("图片数组转化为image失败", e);
            return null;
        }
        return bufferedImage;
    }

    /**
     * 获取图片base64
     *
     * @param imageUrl
     *
     * @return
     */
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
            //设置读取超时为60秒
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

    /**
     * 将输入流转化为 byte[]数组
     *
     * @param inStream
     *
     * @return
     */
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

    /**
     * 图片文件读取
     *
     * @param srcImgPath 文件存放路径
     *
     * @return
     */
    private static BufferedImage InputImage(String srcImgPath) {
        BufferedImage srcImage = null;
        try {
            FileInputStream in = new FileInputStream(srcImgPath);
            srcImage = ImageIO.read(in);
        } catch (IOException e) {
            System.out.println("读取图片文件出错！" + e.getMessage());
            e.printStackTrace();
        }
        return srcImage;
    }

    /**
     * * 将图片按照指定的图片尺寸压缩
     *
     * @param srcImgPath :源图片路径
     * @param outImgPath :输出的压缩图片的路径
     * @param new_w      :压缩后的图片宽
     * @param new_h      :压缩后的图片高
     */
    public static void compressImage(String srcImgPath, String outImgPath,
                                     int new_w, int new_h) {
        BufferedImage src = InputImage(srcImgPath);
        disposeImage(src, outImgPath, new_w, new_h);
    }

    /**
     * 指定长或者宽的最大值来压缩图片
     *
     * @param srcImgPath :源图片路径
     * @param outImgPath :输出的压缩图片的路径
     * @param maxLength  :长或者宽的最大值
     */
    public static void compressImage(String srcImgPath, String outImgPath, int maxLength) {
        // 得到图片
        BufferedImage src = InputImage(srcImgPath);
        if (null != src) {
            int old_w = src.getWidth();
            // 得到源图宽
            int old_h = src.getHeight();
            // 得到源图长
            int new_w = 0;
            // 新图的宽
            int new_h = 0;
            // 新图的长
            // 根据图片尺寸压缩比得到新图的尺寸
            if (old_w > old_h) {
                // 图片要缩放的比例
                new_w = maxLength;
                new_h = (int) Math.round(old_h * ((float) maxLength / old_w));
            } else {
                new_w = (int) Math.round(old_w * ((float) maxLength / old_h));
                new_h = maxLength;
            }
            disposeImage(src, outImgPath, new_w, new_h);
        }
    }

    /**
     * 处理图片
     *
     * @param src
     * @param outImgPath
     * @param new_w
     * @param new_h
     */
    private synchronized static void disposeImage(BufferedImage src, String outImgPath, int new_w, int new_h) {
        // 得到图片
        int old_w = src.getWidth();
        // 得到源图宽
        int old_h = src.getHeight();
        // 得到源图长
        BufferedImage newImg = null;
        // 判断输入图片的类型
        switch (src.getType()) {
            case 13:
                //png,gif
                newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_4BYTE_ABGR);
                break;
            default:
                newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
                break;
        }
        Graphics2D g = newImg.createGraphics();
        // 从原图上取颜色绘制新图
        g.drawImage(src, 0, 0, old_w, old_h, null);
        g.dispose();
        // 根据图片尺寸压缩比得到新图的尺寸
        newImg.getGraphics().drawImage(
                src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0,
                null);
        // 调用方法输出图片文件
        OutImage(outImgPath, newImg);
    }

    /**
     * 将图片文件输出到指定的路径，并可设定压缩质量
     *
     * @param outImgPath
     * @param newImg
     */
    private static void OutImage(String outImgPath, BufferedImage newImg) {
        // 判断输出的文件夹路径是否存在，不存在则创建
        File file = new File(outImgPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }// 输出到文件流
        try {
            ImageIO.write(newImg, outImgPath.substring(outImgPath.lastIndexOf(".") + 1),
                    new File(outImgPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, String> readfile(String filepath, Map<Integer, String> pathMap) throws Exception {
        if (pathMap == null) {
            pathMap = new HashMap<Integer, String>();
        }

        File file = new File(filepath);
        // 文件
        if (!file.isDirectory()) {
            pathMap.put(pathMap.size(), file.getPath());

        } else if (file.isDirectory()) { // 如果是目录， 遍历所有子目录取出所有文件名
            String[] fileList = file.list();
            for (int i = 0; i < fileList.length; i++) {
                File readfile = new File(filepath + "/" + fileList[i]);
                if (!readfile.isDirectory()) {
                    pathMap.put(pathMap.size(), readfile.getPath());

                } else if (readfile.isDirectory()) { // 子目录的目录
                    readfile(filepath + "/" + fileList[i], pathMap);
                }
            }
        }
        return pathMap;
    }


    /**
     * 图片压缩
     *
     * @param source  源文件
     * @param quality 质量压缩比
     * @param targetW 新的长度
     * @param targetH 新的宽度
     *
     * @return
     */
    public static byte[] resize(byte[] source, float quality, int targetW, int targetH) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(source);
        BufferedImage bufferedImageSource = ImageIO.read(inputStream);
        byte[] result = null;
        if (null != bufferedImageSource) {
            int oldW = bufferedImageSource.getWidth();
            // 得到源图宽
            int oldH = bufferedImageSource.getHeight();
            // 得到源图长
            int newW = 0;
            // 新图的宽
            int newH = 0;
            // 新图的长

            if (targetH == 0 || targetW == 0) {
                targetH = oldH;
                targetW = oldW;
            }
            double w2 = (oldW * 1.00) / (targetW * 1.00);
            double h2 = (oldH * 1.00) / (targetH * 1.00);
            // 根据图片尺寸压缩比得到新图的尺寸
            if (oldW > targetW) {
                newW = (int) Math.round(oldW / w2);
            } else {
                newW = oldW;
            }
            if (oldH > targetH) {
                newH = (int) Math.round(oldH / h2);//计算新图长宽
            } else {
                newH = oldH;
            }

            BufferedImage imageSave = null;
            // 判断输入图片的类型
            switch (bufferedImageSource.getType()) {
                case 13:
                    //png,gif
                    imageSave = new BufferedImage(newW, newH, BufferedImage.TYPE_4BYTE_ABGR);
                    break;
                default:
                    imageSave = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
                    break;
            }

            Graphics2D g = imageSave.createGraphics();
            // 从原图上取颜色绘制新图
            g.drawImage(bufferedImageSource, 0, 0, oldW, oldH, null);
            g.dispose();
            // 根据图片尺寸压缩比得到新图的尺寸
            imageSave.getGraphics().drawImage(bufferedImageSource.getScaledInstance(newW, newH, Image.SCALE_SMOOTH), 0, 0, null);

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ImageIO.write(imageSave, "jpg", outStream);
            result = outStream.toByteArray();
        }
        return result;
    }
}
