package com.qxd.app.web;

import common.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiangDong.qu on 15/10/26.
 */
@RequestMapping("/web/login")
@Controller
public class LoginController {

    @ResponseBody
    @RequestMapping("/loginIn")
    public Result login() {
        return Result.wrapSuccessfulResult("true loginIn");
    }

    @RequestMapping("/loginOut")
    public String loginOut() {
        return "loginOut";
    }

}
