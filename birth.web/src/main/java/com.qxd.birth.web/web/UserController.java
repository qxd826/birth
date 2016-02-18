package com.qxd.birth.web.web;

import com.qxd.birth.biz.common.DefaultPage;
import com.qxd.birth.biz.user.UserService;
import com.qxd.birth.common.common.Result;
import com.qxd.birth.dal.entity.master.user.User;
import com.qxd.birth.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by xiangDong.qu on 16/2/17.
 */
@Controller
@Slf4j
@RequestMapping("/web/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public String addUser() {
        return "user/addUser";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result saveUser(@RequestBody User user) {
        if (user == null) {
            log.error("[用户] 添加用户为空.");
            return Result.wrapErrorResult("", "参数错误");
        }
        return userService.saveUser(user);
    }

    @RequestMapping("/getUsers")
    @ResponseBody
    public Result getUsers(@RequestParam(value = "code", defaultValue = "1", required = false) String code) {
        log.info("[用户] 获取用户列表. code={}", code);
        List<User> userList = userService.getUserList();
        if (CollectionUtils.isEmpty(userList)) {
            log.error("[用户] 获取用户列表为空.");
            return Result.wrapErrorResult("", "获取用户列表为空");
        }
        log.info("[用户] 获取用户列表. result={}", userList);
        return Result.wrapSuccessfulResult(userList);
    }

    @RequestMapping("/getUserList")
    @ResponseBody
    public Result getUserList(@PageableDefault(page = 1, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        DefaultPage page = (DefaultPage<User>) userService.getUserListPage(pageable);
        return Result.wrapSuccessfulResult(page);
    }
}
