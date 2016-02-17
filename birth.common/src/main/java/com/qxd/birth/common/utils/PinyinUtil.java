package com.qxd.birth.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by xiangDong.qu on 16/2/17.
 */
public class PinyinUtil {
    /**
     * 获取首字母（数字和符号等返回#）
     *
     * @param str
     * @return
     */
    public static String getFirstLetter(String str) {
        if (StringUtils.isNotBlank(str)) {
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(str.trim().charAt(0));
            if (null != pinyinArray) {
                return (pinyinArray[0].charAt(0) + "").toUpperCase();
            } else {
                String strFirst = str.charAt(0) + "";
                if (strFirst.matches("^[a-zA-Z]+$")) {
                    return strFirst.toUpperCase();
                } else {
                    return "#";
                }
            }
        }
        return "#";
    }
}
