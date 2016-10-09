package com.qxd.birth.biz.test.junitTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
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
@Slf4j
public class JunitTestTwo {

    //配置数据源
    @Resource(name = "dataSourceMaster")
    private DataSource dataSource;

    @Test
    public void stepOne() {
        log.info("stepOne");
    }
}
