package com.qxd.birth.biz.test.categoryTest;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by xiangqong.qu on 16/9/28 21:10.
 */
@Suite.SuiteClasses({ATest.class, BTest.class})
@Categories.IncludeCategory(SlowTests.class)
@RunWith(Categories.class)
public class SlowTestSuiteOne {
    //只会触发ATest.class,BTest.class  中标记为SlowTests.class的方法
    //a.a(),a.b(),b.c()会执行
    //a.c()不会执行
}

/**
 * Category：Category同样继承自Suit，Category似乎是Suit的加强版，
 * 它和Suit一样提供了将若干测试用例类组织成一组的能力，除此以外它可以对各个测试用例进行分组，
 * 使你有机会只选择需要的部分用例。举个例子Person有获取age和name的方法也有talk和walk方法，
 * 前者用于获取属性后者是Person的行为，Category使我们可以只运行属性测试，反之亦然。
 * 首先修改最初的测试用例PersonTest，添加Category信息，
 * 代码如下在每个用例上添加了@Category信息标识它们是用作Attribute还是Behavior的测试，这不会影响原有用例测运行。
 */
