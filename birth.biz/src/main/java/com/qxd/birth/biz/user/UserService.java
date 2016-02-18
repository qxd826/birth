package com.qxd.birth.biz.user;

import com.qxd.birth.biz.base.BaseService;
import com.qxd.birth.common.common.Result;
import com.qxd.birth.dal.entity.master.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by xiangDong.qu on 16/2/17.
 */
public interface UserService extends BaseService {
    /**
     * 获取用户列表
     */
    public List<User> getUserList();

    /**
     * 获取用户列表 分页
     */
    public Page<User> getUserListPage(Pageable pageable);

    /**
     * 添加用户
     *
     * @param user 用户对象
     */
    public Result saveUser(User user);
}
