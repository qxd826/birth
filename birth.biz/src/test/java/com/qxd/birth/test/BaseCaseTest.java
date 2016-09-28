package com.qxd.birth.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xiangqong.qu on 16/9/28 14:35.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@ContextConfiguration(locations = "classpath:junit-biz-context.xml")
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)  //选择数据库
@Transactional
public class BaseCaseTest {
    @Before
    public void before() {
        log.info(this.getClass().getName() + " :: before");
    }

    @After
    public void after() {
        log.info(this.getClass().getName() + " :: after");
    }

    @BeforeClass
    public static void beforeClass() {
        log.info("beforeClass");
    }

    @AfterClass
    public static void afterClass() {
        log.info("afterClass");
    }
}

/*
* 注解说明
*@ContextConfiguration(locations = "classpath:applicationContext.xml")导入配置文件。
* 这时候，我们可以看出之前使用applicationContext.xml文件作为系统总控文件的好处！
* 当然，Spring-Test的这个配置只认classpath，很无奈，我必须拷贝这些文件到根目录！
*@RunWith(SpringJUnit4ClassRunner.class)SpringJUnit支持，由此引入Spring-Test框架支持！
*@Transactional这个非常关键，如果不加入这个注解配置，事务控制就会完全失效！
*@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
* 这里的事务关联到配置文件中的事务控制器（transactionManager = "transactionManager"），
* 同时指定自动回滚（defaultRollback = true）。这样做操作的数据才不会污染数据库！
* */

