package com.qxd.birth.test.categoryTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Created by xiangqong.qu on 16/9/28 21:02.
 */
@Slf4j
public class ATest {

    @Category({FastTests.class, SlowTests.class})
    @Test
    public void a() {

    }

    //定义类 用来标示某种行为 就是打个标签  可以标记类也可以标记方法
    @Category(SlowTests.class)
    @Test
    public void b() {

    }

    @Test
    public void c() {

    }
}
