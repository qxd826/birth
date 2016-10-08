package com.qxd.birth.common.aes;

import com.qxd.birth.common.BaseTest;
import com.qxd.birth.common.utils.AESUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by xiangqong.qu on 16/10/8 15:47.
 */
@Slf4j
public class AesTest extends BaseTest {
    private String testKey = "123456";

    @Test
    public void aesTest() {
        String s = "ahaihdaofhoafoaad飒飒水电费是否!#$!%@$^$%&%^*&*)*()?{}{|.''\';;;k;kp:<>?/";
        String enStr = AESUtils.encrypt(s, testKey);
        log.info("enStr:{}", enStr);

        enStr = enStr.replaceAll("&&", ",");

        String deStr = AESUtils.decrypt(enStr, testKey);
        log.info("deStr:{}", deStr);

        Assert.assertEquals(s, deStr);
    }
}
