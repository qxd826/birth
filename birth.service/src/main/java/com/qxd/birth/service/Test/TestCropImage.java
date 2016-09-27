package com.qxd.birth.service.Test;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xiangqong.qu on 16/9/26 20:57.
 */
@Slf4j
public class TestCropImage {

    /**
     * 缩放图片(压缩图片质量，改变图片尺寸)
     * 若原图宽度小于新宽度，则宽度不变！
     *
     * @param newWidth 新的宽度
     * @param quality  图片质量参数 0.7f 相当于70%质量
     *                 2015年12月11日
     */
    public static void resize(File originalFile, File resizedFile, int newWidth, float quality) throws IOException {

        if (quality > 1) {
            throw new IllegalArgumentException("Quality has to be between 0 and 1");
        }

        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;

        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);

        if (iWidth < newWidth) {
            newWidth = iWidth;
        }
        if (iWidth > iHeight) {
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
        } else {
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH);
        }

        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = {0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0};
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);

        // Write the jpeg to a file.
        FileOutputStream out = new FileOutputStream(resizedFile);

        // Encodes image as a JPEG data stream
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);

        param.setQuality(quality, true);

        encoder.setJPEGEncodeParam(param);
        encoder.encode(bufferedImage);
    }

    // Example usage
    public static void main(String[] args) throws IOException {
        //       File originalImage = new File("C:\\11.jpg");
        //       resize(originalImage, new File("c:\\11-0.jpg"),150, 0.7f);
        //       resize(originalImage, new File("c:\\11-1.jpg"),150, 1f);
        File originalImage = new File("d:\\testImg\\1.jpg");
        System.out.println("源文件大小" + originalImage.length());
        //         File resizedImg = new File("d:\\testImg\\11.jpg");
        //         resize(originalImage, resizedImg, 850, 1f);
        //         System.out.println("0.5转换后文件大小" + resizedImg.length());
        //         File resizedImg1 = new File("d:\\testImg\\111.jpg");
        File resizedImg1 = new File("/alidata/zkyj/dashixiong/tempImgFile/11.jpg");
        resize(originalImage, resizedImg1, 1550, 0.7f);
        System.out.println("0.7转换后文件大小" + resizedImg1.length());
    }
}
