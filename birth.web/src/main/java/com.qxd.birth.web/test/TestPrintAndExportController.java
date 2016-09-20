package com.qxd.birth.web.test;

import com.qxd.birth.dal.pojo.TestUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangqong.qu on 16/9/20 08:39.
 */
@Controller
@RequestMapping(value = "/test/printAndExport")
@Slf4j
public class TestPrintAndExportController {

    /**
     * 测试打印
     *
     * @param request
     * @param model
     *
     * @return
     */
    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public Object testPrint(HttpServletRequest request, Model model) {
        TestUser testUser = new TestUser();
        testUser.setUserName("QXD");
        testUser.setUserMobile("15158116453");
        model.addAttribute("user", testUser);
        return "/test/testPrintFtl";
    }

    /**
     * 测试导出
     *
     * @param request
     * @param response
     *
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public Object testExport(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("application/x-msdownload");
        String filename = "testExport";
        try {
            filename = java.net.URLEncoder.encode("测试导出excel", "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        response.setHeader("Content-Disposition", "attachment;filename= " + filename + ".xls");


        List<TestUser> testUserList = new ArrayList<>();
        TestUser testUser = new TestUser();
        testUser.setUserName("QXD");
        testUser.setUserMobile("15158116453");

        TestUser testUser1 = new TestUser();
        testUser1.setUserName("XYY");
        testUser1.setUserMobile("15757115354");

        testUserList.add(testUser);
        testUserList.add(testUser1);

        ModelAndView modelAndView = new ModelAndView("/test/testExportFtl");
        modelAndView.addObject("userList", testUserList);
        return modelAndView;
    }
}
