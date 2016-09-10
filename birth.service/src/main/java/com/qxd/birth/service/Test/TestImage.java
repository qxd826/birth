package com.qxd.birth.service.Test;

import com.qxd.birth.common.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

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
            String imgFilePath = "/Users/admin/223.jpg";//新生成的图片
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
}
