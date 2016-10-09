package com.qxd.birth.biz.test.suittest;

import com.qxd.birth.biz.test.junitTest.JunitTestOne;
import com.qxd.birth.biz.test.junitTest.JunitTestTwo;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by xiangqong.qu on 16/9/28 20:36.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        JunitTestOne.class,
        JunitTestTwo.class})
public class SuiteTest {

}

/**
 * Suit：没错，Suit就是个Runner！用来执行分布在多个类中的测试用例，
 * 比如我存在SimpleFunctionTest和ComplexFunctionTest类分别测试Person的简单和复杂行为，
 * 在茫茫的测试用例中如何一次执行所有与Person有关的测试呢——使用Suit。
 * 代码如下：其中ComplexFunctionTest和SimpleFunctionTest就是两个普通的测试用例类，这里忽略。
 */
