package com.qxd.birth.common.qrCode;

import com.qxd.birth.common.BaseTest;
import com.qxd.birth.common.utils.QROrBarCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * Created by xiangqong.qu on 16/10/10 21:05.
 */
@Slf4j
public class CodeTest extends BaseTest {
    private File logoFile = new File("/Users/admin/Documents/testImage/logo.jpg");

    @Test
    public void qrTest() {
        QROrBarCodeUtil.QRConfig qrConfig = QROrBarCodeUtil.getQRconfig();

        try {
            File file = new File("/Users/admin/Documents/testImage/qr.jpg");
            QROrBarCodeUtil.QRenCodeFile("http://www.baidu.com", file, "jpg", null);

            File fileone = new File("/Users/admin/Documents/testImage/qrOne.jpg");
            QROrBarCodeUtil.QRenCodeFile("http://www.baidu.com", fileone, "jpg", ImageIO.read(logoFile));

            String str = QROrBarCodeUtil.QRdeCodeFile(file);
            String strOne = QROrBarCodeUtil.QRdeCodeFile(fileone);

            log.info("str:{}  >>>>>>>>>>>>   strOne:{}", str, strOne);
        } catch (Exception e) {
            log.error("生成二维码异常", e);
        }
    }

}
