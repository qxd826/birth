package com.qxd.birth.biz.user.impl;

import com.qxd.birth.biz.base.impl.BaseServiceImpl;
import com.qxd.birth.biz.common.DefaultPage;
import com.qxd.birth.biz.user.UserService;
import com.qxd.birth.common.common.Result;
import com.qxd.birth.dal.dao.master.user.UserDao;
import com.qxd.birth.dal.entity.master.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    @Override
    public Page<User> getUserListPage(Pageable pageable) {
        Integer totalSize = userDao.selectCount(null);
        Map<String, Object> searchParam = new HashMap<>();
        if (null != pageable.getSort()) {
            Iterator<Sort.Order> iterator = pageable.getSort().iterator();
            ArrayList<String> sorts = new ArrayList<>();
            while (iterator.hasNext()) {
                Sort.Order order = iterator.next();
                sorts.add(order.getProperty() + " " + order.getDirection().name());
            }
            searchParam.put("sorts", sorts);
        }

        PageRequest pageRequest = new PageRequest((pageable.getPageNumber() < 1 ? 1 : pageable.getPageNumber()) - 1, pageable.getPageSize() < 1 ? 1 : pageable.getPageSize(), pageable.getSort());

        searchParam.put("limit", pageRequest.getPageSize());
        searchParam.put("offset", pageRequest.getOffset());

        List<User> users = userDao.select(searchParam);
        DefaultPage<User> page = new DefaultPage<>(users, pageRequest, totalSize);
        return page;
    }

    @Override
    public Result saveUser(User user) {
        try {
            userDao.insert(user);
        } catch (Exception e) {
            log.error("[用户] 添加用户失败.user={}", user);
            return Result.wrapErrorResult("", "添加用户失败");
        }
        return Result.wrapSuccessfulResult("添加成功");
    }
}
