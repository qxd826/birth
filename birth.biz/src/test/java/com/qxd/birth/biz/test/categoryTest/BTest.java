package com.qxd.birth.biz.test.categoryTest;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Created by xiangqong.qu on 16/9/28 21:07.
 */
//定义类 用来标示某种行为 就是打个标签  可以标记类也可以标记方法
@Category({SlowTests.class, FastTests.class})
public class BTest {

    @Test
    public void c() {

    }
}
