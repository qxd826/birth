package com.qxd.birth.biz.test.parameterized;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangqong.qu on 16/9/28 21:22.
 */
@RunWith(Parameterized.class)
public class ParameterizedTest {

    @Parameterized.Parameter(value = 0)  //每组参数的第一个参数
    public long p1;
    @Parameterized.Parameter(value = 1)  //每组参数的第二个参数
    public long p2;

    @Parameterized.Parameters
    public static List<Object[]> getParams() {
        List<Object[]> paramDatas = new ArrayList<>();
        paramDatas.add(new Object[]{0, 1});
        paramDatas.add(new Object[]{1, 2});
        paramDatas.add(new Object[]{2, 4});
        return paramDatas;
    }

    @Test
    public void addTest() {
        Assert.assertEquals(p2, p1 + 1);
    }
}

/**
 * 在这个用例里，我们首先需要用@RunWith(Parameterized.class)来修饰我们的测试类；接下来提供一组参数，
 * 还记得JUnit的生命周期吗？在每次运行测试方法的时候都会调用Constructor来创建一个实例，
 * 这里参数就是通过Constructor的参数传入的。因此如你所见我们需要一个含有参数的构造函数用于接收参数，
 * 这个参数需要用于跑测试用例所以把它保存做类的变量；然后用@Parameters修饰我们提供参数的静态方法，
 * 它需要返回List<Object[]>，List包含的是参数组，Object[]即按顺序提供的一组参数。
 */
