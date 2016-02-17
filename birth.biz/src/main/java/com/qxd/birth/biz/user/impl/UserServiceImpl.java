package com.qxd.birth.biz.user.impl;

import com.qxd.birth.biz.base.impl.BaseServiceImpl;
import com.qxd.birth.biz.user.UserService;
import com.qxd.birth.dal.dao.master.user.UserDao;
import com.qxd.birth.dal.entity.master.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangDong.qu on 16/2/17.
 */
@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUserList() {
        List<User> users = new ArrayList<User>();
        users = userDao.select(null);
        return users;
    }
}
