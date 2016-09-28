package com.qxd.birth.test.junitTest;

import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by xiangqong.qu on 16/9/28 11:26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:biz-context.xml")
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)  //选择数据库
@Transactional
public class JunitTestTwo {

    //配置数据源
    @Resource(name = "dataSourceMaster")
    private DataSource dataSource;

}
