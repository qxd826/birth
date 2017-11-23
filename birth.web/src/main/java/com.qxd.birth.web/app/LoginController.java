package com.qxd.birth.web.app;

import com.qxd.birth.common.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiangDong.qu on 15/10/26.
 */
@RequestMapping("/app/login")
@Controller
@Slf4j
public class LoginController {


    /**
     * get 方法中尽量使用 PathVariable
     *
     * @param account
     * @param password
     *
     * @return
     */
    @RequestMapping(value = "/loginIn/{account}/{password}", method = RequestMethod.GET)
    @ResponseBody
    public Result login(@PathVariable("account") String account, @PathVariable("password") String password) {
        return Result.wrapSuccessfulResult("true loginIn:" + account + "  password:" + password);
    }

    @RequestMapping("/loginOut")
    public String loginOut() {
        return "index";
    }
}
