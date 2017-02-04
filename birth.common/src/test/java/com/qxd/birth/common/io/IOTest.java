package com.qxd.birth.common.io;

/**
 * Created by xiangdong.qu on 17/1/4 17:09.
 */
public class IOTest {
    public static void main(String... args) {
        System.out.println("test");
        String s = "qxd";
        byte[] b = s.getBytes();
        for (byte temp : b) {
            System.out.println(temp);
        }
    }
}
