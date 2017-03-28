package com.qxd.birth.common.url;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by xiangdong.qu on 17/3/7 11:17.
 */
@Slf4j
public class UrlTest {

    @Test
    public void urlEncodeTest() {
        String url = "http://localhost:8090/legend/html/app/ax_insurance/fail.html?insuranceOrderSn=AEC101123123&totalInsuredFee=10.00";
        String urlEncode = null;
        String urlEncodeRe = null;
        String decodeRe = null;
        String decode = null;
        try {
            urlEncode = URLEncoder.encode(url, "UTF-8");
            urlEncodeRe = URLEncoder.encode(urlEncode, "UTF-8");
            decodeRe = URLDecoder.decode(urlEncodeRe, "UTF-8");
            decode = URLDecoder.decode(decodeRe, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        Assert.assertFalse(StringUtils.equals(url, urlEncode));
    }
}
