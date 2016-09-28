package com.qxd.birth.test.junitTest;

import com.qxd.birth.biz.user.UserService;
import com.qxd.birth.dal.entity.master.user.User;
import com.qxd.birth.test.BaseCaseTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.*;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangqong.qu on 16/9/28 11:11.
 */
@Slf4j
public class JunitTestOne extends BaseCaseTest {

    /**
     * 当编写测试方法时，经常会发现一些方法在执行前需要创建相同的对象
     * 使用@Before注解一个public void 方法会使该方法在@Test注解方法被执行前执行
     * 有多少个@Test方法就会执行多少次
     * (那么就可以在该方法中创建相同的对象)
     * 父类的@Before注解方法会在子类的@Before注解方法执行前执行
     */
    @Before
    public void before() {
        log.info(">>>>>>>>>>>>>>>>before");
    }

    /**
     * 如果在@Before注解方法中分配了额外的资源，那么在测试执行完后，需要释放分配的资源。
     * 使用@After注解一个public void方法会使该方法在@Test注解方法执行后被执行
     * 有多少个@Test方法就会执行多少次
     * 即使在@Before注解方法、@Test注解方法中抛出了异常，所有的@After注解方法依然会被执行,见示例
     * 父类中的@After注解方法会在子类@After注解方法执行后被执行
     */
    @After
    public void after() {
        log.info(">>>>>>>>>>>>>>>>after");
    }

    /**
     * 有些时候，一些测试需要共享代价高昂的步骤（如数据库登录），这会破坏测试独立性，通常是需要优化的
     * 使用@BeforeClass注解一个public static void 方法，并且该方法不带任何参数，会使该方法在所有测试方法被执行前执行一次，并且只执行一次
     * 父类的@BeforeClass注解方法会在子类的@BeforeClass注解方法执行前执行
     */
    @BeforeClass
    public static void beforeClass() {
        log.info(">>>>>>>>>>>>>>>>beforeClass");
    }

    /**
     * 如果在@BeforeClass注解方法中分配了代价高昂的额外的资源，那么在测试类中的所有测试方法执行完后，需要释放分配的资源。
     * 使用@AfterClass注解一个public static void方法会使该方法在测试类中的所有测试方法执行完后被执行
     * 即使在@BeforeClass注解方法中抛出了异常，所有的@AfterClass注解方法依然会被执行
     * 父类中的@AfterClass注解方法会在子类@AfterClass注解方法执行后被执行
     */
    @AfterClass
    public static void afterClass() {
        log.info(">>>>>>>>>>>>>>>>afterClass");
    }

    @Resource
    private UserService userService;

    /**
     * 数据源
     */
    @Resource(name = "dataSourceMaster")
    private DataSource dataSource;

    /**
     * @Test注解的public void方法将会被当做测试用例
     * JUnit每次都会创建一个新的测试实例，然后调用@Test注解方法
     * 任何异常的抛出都会认为测试失败
     * Test注解提供2个参数:
     * 1.“expected”，定义测试方法应该抛出的异常，如果测试方法没有抛出异常或者抛出了一个不同的异常，测试失败
     * 2.“timeout”，如果测试运行时间长于该定义时间，测试失败（单位为毫秒）
     */
    @Test
    public void userServiceTest() {
        List<Map<String, Object>> resultList = addUserAndGet();
        Assert.assertTrue(resultList.size() > 0);
    }


    /**
     * 对包含测试类的类或@Test注解方法使用@Ignore注解将使被注解的类或方法不会被当做测试执行
     * JUnit执行结果中会报告被忽略的测试数
     */
    @Ignore
    @Test
    public void addTest() {
        Assert.assertEquals(1, 0);
    }

    /**
     * “expected”，定义测试方法应该抛出的异常，如果测试方法没有抛出异常或者抛出了一个不同的异常，测试失败
     *
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void exceptionTest() throws Exception {
        throw new Exception();
    }


    /**
     * “timeout”，如果测试运行时间长于该定义时间，测试失败（单位为毫秒）
     */
    @Test(timeout = 5000)
    public void timeOutTest() {
        while (true) {

        }
    }

    /**
     * Rollback 操作回滚
     * 需要
     * TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)  //选择数据库
     * Transactional
     * 注解 :见BaseCaseTest
     * 总结:
     * 1.只配置Rollback 或 只配置TransactionConfiguration 或两者都配置 都不会引起回滚
     * 必须配置Transactional 注解
     * 2.TransactionConfiguration 定义某个特定的数据管理器,关联到数据库
     * 默认该数据库的操作都回滚 defaultRollback = true
     * 如果该数据管理 配置为defaultRollback = true 则不管使用该数据源的测试方法是否添加 rollback注解都会回滚
     * 3.如果defaultRollback = false 则可以在具体的使用方法上 通过Rollback注解来实现回滚
     * 例如userServiceTestRollBack()
     */
    @Test
    @Rollback
    public void userServiceTestRollBack() {
        List<Map<String, Object>> resultList = addUserAndGet();
        Assert.assertTrue(resultList.size() > 0);
    }

    private List<Map<String, Object>> addUserAndGet() {
        User user = new User();
        user.setMobile("15158116454");
        user.setName("lqu");
        userService.saveUser(user);

        String sql = "select * from love_user where is_deleted='N' and name = 'lqu' ";
        return querySqL(sql);
    }

    public List<Map<String, Object>> querySqL(String sql) {
        List<Map<String, Object>> queryList = null;
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            queryList = queryRunner.query(sql, new MapListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queryList;
    }
}
