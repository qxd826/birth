package com.qxd.birth.web.web;

import com.qxd.birth.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiangDong.qu on 16/2/17.
 */
@Controller
@Slf4j
@RequestMapping("/web/user")
public class UserController extends BaseController {

    @RequestMapping("/add")
    public String addUser() {
        return "user/addUser";
    }
}
