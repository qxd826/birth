package com.qxd.birth.service.test;

import com.qxd.birth.biz.user.UserService;
import com.qxd.birth.dal.entity.master.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by xiangqong.qu on 16/9/29 19:58.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@ContextConfiguration(locations = "classpath:junit-service-context.xml")
@TransactionConfiguration(transactionManager = "transactionManagerMaster", defaultRollback = false)  //选择数据库
@Transactional
public class ServiceTest {

    @Resource
    private UserService userService;

    @Test
    @Rollback
    public void addUserServiceTest() {
        User user = new User();
        user.setName("xyy");
        user.setMobile("15158116456");
        userService.saveUser(user);
    }

}
