package com.qxd.birth.common.utils;

/**
 * Created by xiangqong.qu on 16/10/10 10:54.
 */

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.qxd.birth.common.assist.qrcode.BufferedImageLuminanceSource;
import com.qxd.birth.common.assist.qrcode.LogoConfig;
import com.qxd.birth.common.exception.ImageCreateException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码/条形码生成解析
 */
@Slf4j
public class QROrBarCodeUtil {

    //默认二维码宽度
    private static final int QR_WIDTH_HOLDER = 300;
    //默认二维码高度
    private static final int QR_HEIGHT_HOLDER = 300;
    //黑色
    private static final int BLACK = 0xFF000000;
    //白色
    private static final int WHITE = 0xFFFFFFFF;

    //条码/二维码的图片格式
    private static final String IMAGE_FORMAT = "png";

    //默认编码方式
    private static final String CHAR_SET = "UTF-8";

    /**
     * @param content   二维码内容
     * @param logoImage logo图标
     *
     * @return
     */
    public static BufferedImage QRenCodeImage(String content, BufferedImage logoImage) {
        BufferedImage image = null;
        /**
         * 设置二维码的参数
         */
        HashMap hints = new HashMap();
        // 内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, CHAR_SET);
        // 指定纠错等级 纠错等级高 二维码更复杂
        if (null != logoImage) {
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        } else {
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        }

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QR_WIDTH_HOLDER, QR_HEIGHT_HOLDER, hints);
            // 生成二维码
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
                }
            }
            //添加logo
            if (null != logoImage) {
                addLogo(image, logoImage, new LogoConfig());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return image;
    }

    /**
     * 生成二维码图片byte数组
     *
     * @param content   二维码内容
     * @param logoImage logo图标
     *
     * @return
     */
    public static byte[] QRenCodeByte(String content, BufferedImage logoImage) {
        BufferedImage bufferedImage = QRenCodeImage(content, logoImage);
        if (null == bufferedImage) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, IMAGE_FORMAT, byteArrayOutputStream);
        } catch (Exception e) {
            log.error("图片转为byte数组失败", e);
            return null;
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 生成二维码图片文件
     *
     * @param content     二维码内容
     * @param file        生成文件
     * @param imageFormat 生成图片格式
     * @param logoImage   logo图标
     *
     * @return
     *
     * @throws Exception
     */
    public static boolean QRenCodeFile(String content, File file, String imageFormat, BufferedImage logoImage) throws Exception {
        BufferedImage bufferedImage = QRenCodeImage(content, logoImage);
        if (null == bufferedImage) {
            throw new ImageCreateException("参数错误");
        }
        if (null == file) {
            throw new ImageCreateException("参数错误");
        }

        if (!ImageIO.write(bufferedImage, imageFormat, file)) {
            throw new IOException("Could not write an image of format " + imageFormat + " to " + file);
        }
        return true;
    }

    /**
     * 生成二维码图片输出流
     *
     * @param content      二维码内容
     * @param outputStream 输出流
     * @param imageFormat  图片格式
     * @param logoImage    logo图标
     *
     * @return
     *
     * @throws Exception
     */
    public static boolean QRenCodeOutputStream(String content, OutputStream outputStream, String imageFormat, BufferedImage logoImage) throws Exception {
        BufferedImage bufferedImage = QRenCodeImage(content, logoImage);
        if (null == bufferedImage) {
            throw new ImageCreateException("参数错误");
        }
        if (null == outputStream) {
            throw new ImageCreateException("参数错误");
        }

        if (!ImageIO.write(bufferedImage, imageFormat, outputStream)) {
            throw new IOException("Could not write an image of format " + imageFormat);
        }
        return true;
    }


    /**
     * 为二维码图片添加logo
     *
     * @param sourceImage 源图片
     * @param logoImage   logo图片
     * @param logoConfig  logoConfig
     */
    public static void addLogo(BufferedImage sourceImage, BufferedImage logoImage, LogoConfig logoConfig) {
        Graphics2D g = sourceImage.createGraphics();
        /**
         * 设置logo的大小,本人设置为二维码图片的20%,因为过大会盖掉二维码
         */
        int widthLogo = logoImage.getWidth(null) > sourceImage.getWidth() * 2 / 10 ? (sourceImage.getWidth() * 2 / 10) : logoImage.getWidth(null);
        int heightLogo = logoImage.getHeight(null) > sourceImage.getHeight() * 2 / 10 ? (sourceImage.getHeight() * 2 / 10) : logoImage.getHeight(null);

        // 计算图片放置位置
        /**
         * logo放在中心
         */
        int x = (sourceImage.getWidth() - widthLogo) / 2;
        int y = (sourceImage.getHeight() - heightLogo) / 2;
        /**
         * logo放在右下角
         */
        /*int x = (sourceImage.getWidth() - widthLogo);
        int y = (sourceImage.getHeight() - heightLogo);*/
        //开始绘制图片
        g.drawImage(logoImage, x, y, widthLogo, heightLogo, null);
        g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
        g.setStroke(new BasicStroke(logoConfig.getBorder()));
        g.setColor(logoConfig.getBorderColor());
        g.drawRect(x, y, widthLogo, heightLogo);

        g.dispose();
        logoImage.flush();
        sourceImage.flush();
    }

    /**
     * 二维码解码
     *
     * @param bufferedImage 图片
     *
     * @return
     */
    public static String QRdeCodeImage(BufferedImage bufferedImage) {
        String resultStr = "";
        try {
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, CHAR_SET);
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            resultStr = result.getText();
        } catch (Exception e) {
            log.error("二维码解码失败", e);
        }
        return resultStr;
    }

    /**
     * 二维码解码
     *
     * @param file 文件对象
     *
     * @return
     */
    public static String QRdeCodeFile(File file) {
        String resultStr = "";
        if (!file.exists()) {
            return resultStr;
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            resultStr = QRdeCodeImage(bufferedImage);
        } catch (Exception e) {
            log.error("二维码解码失败", e);
        }
        return resultStr;
    }

    /**
     * 二维码解码
     *
     * @param imageByteArray 文件数组
     *
     * @return
     */
    public static String QRdeCodeByteArray(byte[] imageByteArray) {
        String resultStr = "";
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteArray));
            resultStr = QRdeCodeImage(bufferedImage);
        } catch (Exception e) {
            log.error("二维码解码失败", e);
        }
        return resultStr;
    }

    /**
     * 二维码解码
     *
     * @param inputStream 文件输入流
     *
     * @return
     */
    public static String QRdeCodeInputStream(InputStream inputStream) {
        String resultStr = "";
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            resultStr = QRdeCodeImage(bufferedImage);
        } catch (Exception e) {
            log.error("二维码解码失败", e);
        }
        return resultStr;
    }

    @Data
    public static class QRConfig {
        //默认高度
        private int height = QR_HEIGHT_HOLDER;
        //默认宽度
        private int weight = QR_WIDTH_HOLDER;
        //默认背景色
        private int backColor = 0xFF000000;
        //默认前景色
        private int frontColor = 0xFFFFFFFF;
        //默认图片格式
        private String imageFormat = "png";
        //默认
        private String charSet = "utf-8";
        //默认logo
        private BufferedImage logoImage;
    }

    public static QRConfig getQRconfig() {
        return new QRConfig();
    }
}
