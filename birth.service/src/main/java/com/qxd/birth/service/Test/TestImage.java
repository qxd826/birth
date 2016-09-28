package com.qxd.birth.service.Test;

import com.qxd.birth.common.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xiangdong.qu on 16/9/9 20:02.
 */
@Slf4j
public class TestImage {

    public static void test() {
        String base64Str = ImageUtils.getImageBase64Str("http://www.easyicon.net/api/resizeApi.php?id=1189109&size=48");
        BASE64Decoder decoder = new BASE64Decoder();
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            //图片存储
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64Str);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = "/Users/admin/223.png";//新生成的图片
            File file = new File(imgFilePath);
            OutputStream out = new FileOutputStream(file);
            out.write(b);
            out.flush();
            out.close();


            //反转
            FileInputStream fis = null;
            byte[] bt = null;
            File fileTemp = new File(imgFilePath);
            if (!fileTemp.exists() || fileTemp.isDirectory()) {
                throw new FileNotFoundException();
            }
            fis = new FileInputStream(fileTemp);
            bt = new byte[(int) fileTemp.length()];
            fis.read(bt);
            String str = encoder.encode(bt);

            fis.close();
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 图片压缩
     *
     * @param source  源文件
     * @param quality 压缩比
     * @param targetW 新的长度
     * @param targetH 新的宽度
     *
     * @return
     */
    public static byte[] resize(byte[] source, float quality, int targetW, int targetH) throws Exception {
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

    public static void testResize() throws Exception {
        String imageUrl = "http://tqmall-legend-img.oss-cn-hangzhou.aliyuncs.com/merchant/app/2016921/1_1474457406.jpg";

        long startTime = System.currentTimeMillis();
        InputStream inStream = null;
        URL url = new URL(imageUrl);
        //打开链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为3秒
        conn.setConnectTimeout(3000);
        //设置读取超时为60秒
        conn.setReadTimeout(60000);
        //通过输入流获取图片数据
        inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = ImageUtils.readInputStream(inStream);

        byte[] targetResult = resize(data, 1, 800, 600);

        long endTime = System.currentTimeMillis();
        log.info("耗时 ms:{}", endTime - startTime);
        //生成jpeg图片
        String imgFilePath = "/Users/admin/Documents/testImage/223.jpg";//新生成的图片
        File file = new File(imgFilePath);
        OutputStream out = new FileOutputStream(file);
        out.write(targetResult);
        out.flush();
        out.close();
    }
}
