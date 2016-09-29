package com.qxd.birth.biz.test.categoryTest;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by xiangqong.qu on 16/9/28 21:14.
 */
@Suite.SuiteClasses({ATest.class, BTest.class})
@Categories.IncludeCategory(SlowTests.class)
@Categories.ExcludeCategory(FastTests.class)
@RunWith(Categories.class)
public class SlowTestSuiteTwo {
    //只会触发ATest.class,BTest.class  中标记为SlowTests.class 并且没有被标记为 FastTests.class 的方法
    //a.b()会执行
    //a.a(),a.c(),b.c()不会执行
}
