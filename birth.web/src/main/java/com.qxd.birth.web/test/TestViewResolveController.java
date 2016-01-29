package com.qxd.birth.web.test;

import com.qxd.birth.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiangDong.qu on 16/1/29.
 */
@Controller
@RequestMapping("/test/viewResolve")
@Slf4j
public class TestViewResolveController extends BaseController {

    @RequestMapping
    public String testView(HttpServletRequest request, HttpServletResponse response) {
        return "welcome";
    }

    @RequestMapping(value = "/testJsp", method = RequestMethod.GET)
    public String testJsp() {
        log.info("[testViewResolve] testJsp", request);
        return "testJsp";
    }

    @RequestMapping(value = "/testFtl", method = RequestMethod.GET)
    public String testFtl() {
        log.info("[testViewResolve] testFtl", request);
        return "testFtl";
    }
}
