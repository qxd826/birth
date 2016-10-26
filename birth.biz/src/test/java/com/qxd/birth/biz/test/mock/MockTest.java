package com.qxd.birth.biz.test.mock;

import com.qxd.birth.dal.entity.master.user.User;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

/**
 * Created by xiangqong.qu on 16/10/18 14:28.
 */
@Slf4j
public class MockTest {
    private static List<String> list;
    private static Iterator iterator;
    private static List<Integer> integerList;
    private static User mockUser;

    @BeforeClass
    public static void beforeClass() {
        log.info("创建mock对象");
        //创建mock对象，参数可以是类，也可以是接口
        list = mock(List.class);
        iterator = mock(Iterator.class);
        integerList = mock(List.class);
        mockUser = mock(User.class);
    }

    @AfterClass
    public static void afterClass() {
        log.info("销毁mock对象");
        list = null;
        iterator = null;
        integerList = null;
    }


    /**
     * 测试方法返回
     */
    @Test
    public void methodTest() {
        //设置方法的预期返回值 stubbing 第一次调用返回helloworld,第二次调用返回haha
        when(list.get(0)).thenReturn("helloworld").thenReturn("haha");

        //调用方法
        String result = list.get(0);

        //验证方法调用(是否调用了get(0))
        verify(list).get(0);

        //junit测试
        assertEquals("helloworld", result);

        String resultOne = list.get(0);

        assertEquals(resultOne, "haha");
    }


    /**
     * 测试对象属性获取
     */
    @Test
    public void userTest() {
        //mock 对象
        when(mockUser.getName()).thenReturn("qxd");

        String userName = mockUser.getName();
        verify(mockUser).getName();
        //verify(mockUser).getMobile();

        assertEquals("qxd", userName);
        assertEquals("xxx", userName);
    }


    /**
     * 测试异常抛出
     */
    @Test
    public void throwAbleTest() {
        //模拟方法调用抛出异常
        when(list.get(0)).thenThrow(new RuntimeException("mock test throw 1"));

        //没有返回值类型的方法也可以模拟异常抛出
        doThrow(new RuntimeException("mock test throw 2")).when(list).clear();

        list.get(0);
        list.clear();
    }


    /**
     * 测试参数匹配
     */
    @Test
    public void methodMatcherTest() {
        when(list.get(anyInt())).thenReturn("element");
        for (int i = 0; i < 3; i++) {
            assertEquals(list.get(i), "element");
        }
    }

    /**
     * 测试方法调用次数
     */
    @Test
    public void methodTimeTest() {
        //调用add一次
        list.add("one");
        //下面两个写法验证效果一样,均验证add方法是否被调用了一次
        verify(list).add("one");
        verify(list, times(1)).add("one");
        //verify(list, times(2)).add("one");

        //还可以通过atLeast(int i)和atMost(int i)来替代time(int i)来验证被调用的次数最小值和最大值。
        //verify(list, atLeast(1)).add("two");
        verify(list, atMost(1)).add("1");

        verify(list, never()).add("three");
        //verify(list, never()).add("1");
    }

    /**
     * 迭代器测试
     */
    @Test
    public void iteratorTest() {
        when(iterator.next()).thenReturn("Hello").thenReturn("World").thenReturn("QXD");
        //act
        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        //assert
        assertEquals("Hello World QXD", result);
    }

    /**
     * 方法的默认返回测试
     * <p>
     * 默认情况下，对于所有有返回值且没有stub过的方法，mockito会返回相应的默认值。
     * 对于内置类型会返回默认值，如int会返回0，布尔值返回false。对于其他type会返回null。
     * 这里一个重要概念就是： mock对象会覆盖整个被mock的对象，因此没有stub的方法只能返回默认值
     */
    @Test
    public void defaultTest() {
        log.info("TEST DEFAULT s:{}", list.get(2));

        log.info("TEST DEFAULT I:{}", integerList.get(0));

        log.info("TEST DEFAULT b:{}", mockUser.getDie());

        log.info("TEST DEFAULT i:{}", mockUser.getTestMock());
    }

    /**
     * 测试 comparable test
     */
    @Test
    public void comparableTest() {
        Comparable c = mock(Comparable.class);
        when(c.compareTo(anyInt())).thenReturn(-1);
        assertEquals(-1, c.compareTo(5));
    }

    /**
     * 对io的测试
     */
    @Test(expected = IOException.class)
    public void ioTest() throws IOException {
        OutputStream mock = mock(OutputStream.class);
        OutputStreamWriter osw = new OutputStreamWriter(mock);
        doThrow(new IOException()).when(mock).close();
        osw.close();
        verify(mock).close();
        //这个也会成功 因为close()方法调用了flush()方法
        verify(mock).flush();
    }


    @Test
    public void OutputStreamWriter_Buffers_And_Forwards_To_OutputStream()
            throws IOException {
        OutputStream mock = mock(OutputStream.class);
        OutputStreamWriter osw = new OutputStreamWriter(mock);
        osw.write('a');
        osw.flush();
        // can't do this as we don't know how long the array is going to be
        // verify(mock).write(new byte[]{'a'},0,1);

        BaseMatcher<byte[]> arrayStartingWithA = new BaseMatcher<byte[]>() {
            @Override
            public void describeTo(Description description) {
                // nothing
            }

            // check that first character is A
            @Override
            public boolean matches(Object item) {
                byte[] actual = (byte[]) item;
                return actual[0] == 'a';
            }
        };
        // check that first character of the array is A,
        // and that the other two arguments are 0 and 1
        verify(mock).write(argThat(arrayStartingWithA), eq(0), eq(1));
    }

    /**
     * 参数匹配测试
     * 这里有一个限制就是，如果在调用方法时需要传入多个参数，其中一个参数使用了argument matcher，那么所有的参数必须都是matcher。
     * 不可以matcher和实际的参数混着用。
     * <p>
     * 这里也可以使用custom argument matcher。因为很多时候输入参数不是build-in 类型，而是我们自己写的一些类，或特殊对象。
     * 这时要使用argument matcher，就必须订制特殊的matcher了。
     * 下例是一个特殊的matcher的实例，这个matcher可以匹配任何file对象。
     */
    @Test
    public void argumentMatcherTest() {
        File mock = mock(File.class); //首先mock File类。
        //注意new IsAnyFiles()并不是一个matcher，需要调用argThat(new IsAnyFiles()))才返回一个matcher。

        //下句中stub：当调用renameTo方法时，返回false。该方法参数可以是任意file对象。
        when(mock.renameTo(argThat(new IsAnyFiles()))).thenReturn(false);
        mock.renameTo(new File("test"));

        //下句verify renameTo方法被调用了一次，同时输入参数是任意file。
        verify(mock).renameTo(argThat(new IsAnyFiles()));
    }

    //自定义matcher
    class IsAnyFiles extends ArgumentMatcher<File> {
        public boolean matches(Object file) {
            return file.getClass() == File.class;
        }
    }

}
