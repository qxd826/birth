package com.qxd.birth.biz.user;

import com.qxd.birth.biz.base.BaseService;
import com.qxd.birth.dal.entity.master.user.User;

import java.util.List;

/**
 * Created by xiangDong.qu on 16/2/17.
 */
public interface UserService extends BaseService {
    /**
     * 获取用户列表
     */
    public List<User> getUserList();
}
