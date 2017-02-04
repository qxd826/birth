package com.qxd.birth.common.array;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by xiangdong.qu on 17/2/4 15:04.
 */
public class ArrayTest {
    public static void main(String... arg) {
        Integer activityId = 154;

        Integer[] integerArray = new Integer[]{154, 388, 424, 494, 577, 639};

        System.out.println(ArrayUtils.contains(integerArray, activityId));

        activityId = 155;

        System.out.println(ArrayUtils.contains(integerArray, activityId));

        activityId = 639;

        System.out.println(ArrayUtils.contains(integerArray, activityId));


        activityId = 640;

        System.out.println(ArrayUtils.contains(integerArray, activityId));
    }
}
