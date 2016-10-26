package com.qxd.birth.biz.test.mock;

import com.qxd.birth.dal.entity.master.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnit44Runner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * 该类主要测试了 Mock注解,spy用法,argumentCaptor参数捕获器(及注解Captor),InjectMocks注解
 * <p>
 * Created by xiangqong.qu on 16/10/21 14:19.
 * Mockito支持对变量进行注解，例如将mock对象设为测试类的属性，
 * 然后通过注解的方式@Mock来定义它，这样有利于减少重复代码，增强可读性，
 * 易于排查错误等。除了支持@Mock，Mockito支持的注解
 * 还有@Spy（监视真实的对象），@Captor（参数捕获器），@InjectMocks（mock对象自动注入）。
 * <p>
 * <p>
 * Annotation的初始化
 * 只有Annotation还不够，要让它们工作起来还需要进行初始化工作。
 * 初始化的方法为：MockitoAnnotations.initMocks(testClass)参数testClass是你所写的测试类。
 * 一般情况下在Junit4的@Before定义的方法中执行初始化工作
 */
@RunWith(MockitoJUnit44Runner.class) //使注解生效
public class MockAnnotationTest {

    /**
     * @Mock注解 使用@Mock注解来定义mock对象有如下的优点：
     * 1. 方便mock对象的创建
     * 2. 减少mock对象创建的重复代码
     * 3. 提高测试代码可读性
     * 4. 变量名字作为mock对象的标示，所以易于排错
     * @Mock注解也支持自定义name和answer属性。
     */
    @Mock
    private static List<String> mockList;

    @Spy
    private static User user = new User();

    @Mock(name = "mockUser")
    private static User mockUser;

    @Before
    public void before() {
        //使注解生效 或者添加@RunWith(MockitoJUnit44Runner.class)
        //MockitoAnnotations.initMocks(this);
    }

    /**
     * 测试mock 注解
     */
    @Test
    public void testMockAnnotation() {
        when(mockList.get(0)).thenReturn("mocktest");
        String s = mockList.get(0);
        assertEquals("mocktest", s);
    }

    /**
     * 测试spy
     */
    @Test
    public void testMockSpy() {
        List<String> spyList = spy(new ArrayList<String>());
        /**
         * stubbing被监视对象的方法时要慎用when(Object)
         * Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
         *
         * 当调用when(spy.get(0)).thenReturn("foo")时，会调用真实对象的get(0)，
         * 由于list是空的所以会抛出IndexOutOfBoundsException异常，用doReturn可以避免这种情况的发生，
         * 因为它不会去调用get(0)方法。
         * */
        when(spyList.get(0)).thenReturn("1");
        doReturn("2").when(spyList).get(0);
        assertEquals("2", spyList.get(0));
    }

    /**
     * spy注解测试
     */
    @Test
    public void testMockSpyUser() {
        //stubbing
        when(user.getMobile()).thenReturn("15158116453");
        //check
        assertEquals("15158116453", user.getMobile());
        //返回真实的结果 spy效果
        assertEquals(2, user.getTestMock());

        //stubbing
        when(user.getTestMock()).thenReturn(3);
        //check
        assertEquals(3, user.getTestMock());

        //返回了默认值   mock对象会覆盖整个被mock的对象，因此没有stub的方法只能返回默认值
        User userTemp = mock(User.class);
        assertEquals(0, userTemp.getTestMock());
    }

    /**
     * 参数捕获测试
     * <p>
     * 在某些场景中，不光要对方法的返回值和调用进行验证，同时需要验证一系列交互后所传入方法的参数。
     * 那么我们可以用参数捕获器来捕获传入方法的参数进行验证，看它是否符合我们的要求。
     * <p>
     * ArgumentCaptor介绍
     * 通过ArgumentCaptor对象的forClass(Class<T> clazz)方法来构建ArgumentCaptor对象。
     * 然后便可在验证时对方法的参数进行捕获，最后验证捕获的参数值。
     * 如果方法有多个参数都要捕获验证，那就需要创建多个ArgumentCaptor对象处理。
     * <p>
     * ArgumentCaptor的Api
     * argument.capture() 捕获方法参数
     * argument.getValue() 获取方法参数值，如果方法进行了多次调用，它将返回最后一个参数值
     * argument.getAllValues() 方法进行多次调用后，返回多个参数值
     * <p>
     * <p>
     * <p>
     * 首先构建ArgumentCaptor需要传入捕获参数的对象，例子中是String。
     * 接着要在verify方法的参数中调用argument.capture()方法来捕获输入的参数，
     * 之后argument变量中就保存了参数值，可以用argument.getValue()获取。当某个对象进行了多次调用后，
     * 如mock2对象，这时调用argument.getValue()获取到的是最后一次调用的参数。
     * 如果要获取所有的参数值可以调用argument.getAllValues()，它将返回参数值的List。
     * <p>
     * 在某种程度上参数捕获器和参数匹配器有很大的相关性。
     * 它们都用来确保传入mock对象参数的正确性。
     * 然而，当自定义的参数匹配器的重用性较差时，用参数捕获器会更合适，只需在最后对参数进行验证即可。
     */
    @Test
    public void testCaptor() {
        mockList.add("xxx");
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        //verify(mockList).add(argument.capture());
        //assertEquals("qxd", argument.getValue());

        mockList.add("yyy");
        mockList.add("zzz");

        verify(mockList, times(3)).add(argument.capture());

        assertEquals("zzz", argument.getValue());
        assertArrayEquals(new Object[]{"xxx", "yyy", "zzz"}, argument.getAllValues().toArray());
    }

}
