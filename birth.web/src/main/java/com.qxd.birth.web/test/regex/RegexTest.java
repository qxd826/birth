package com.qxd.birth.web.test.regex;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiangqong.qu on 16/9/29 09:01.
 */
@Slf4j
public class RegexTest {

    public static void main(String... args) {
        Pattern pattern = Pattern.compile("^qxd.*");
        Matcher matcherOne = pattern.matcher("qxd123");
        Matcher matcherTwo = pattern.matcher("1qxd");
        boolean one = matcherOne.matches();
        boolean two = matcherTwo.matches();

        log.info("matcherOne:{}", one); //true
        log.info("matcherTwo:{}", two); //false

        log.info("matcherTre:{}", Pattern.matches("^qxd", "123"));  //false


        //以qxd为间隔 割接字符串
        String temp = "qxd hello xyy, ssd qxd, hello, qxd love";
        Pattern patternOne = Pattern.compile("qxd");
        String[] strArray = patternOne.split(temp);
        for (int i = 0; i < strArray.length; i++) {
            log.info(strArray[i]);
        }

        String[] strArrayOne = patternOne.split(temp, 2);
        for (int i = 0; i < strArray.length; i++) {
            log.info(strArray[i]);
        }
    }
}
