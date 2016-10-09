package com.qxd.birth.common.utils;

import java.util.Arrays;

/**
 * Created by xiangqong.qu on 16/9/19 17:24.
 */
public class ArrayUtil {


    /**
     * @param first
     * @param second
     * @param <T>
     *
     * @return
     */
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * @param first
     * @param rest
     * @param <T>
     *
     * @return
     */
    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}
