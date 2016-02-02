package com.qxd.birth.web.test;

import com.qxd.birth.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * 在视图上添加属性
     */
    @RequestMapping(value = "/testFtl", method = RequestMethod.GET)
    public String testFtl(Model model, @RequestParam(value = "author", required = false) String author) {
        log.info("[testViewResolve] testFtl. author={}", author);
        if (StringUtils.isBlank(author)) {
            model.addAttribute("author", "xiangdong.qu");
        } else {
            model.addAttribute("author", author);
        }
        return "testFtl";
    }

    /**
     * 新建视图
     */
    @RequestMapping(value = "/testNewFtl", method = RequestMethod.GET)
    public Object testNewFtl(@RequestParam(value = "author", required = false) String author) {
        log.info("[testViewResolve] testNewFtl. author={}", author);
        ModelAndView modelAndView = new ModelAndView("testFtl");
        if (StringUtils.isBlank(author)) {
            modelAndView.addObject("author", "xiangdong.qu");
        } else {
            modelAndView.addObject("author", author);
        }
        return modelAndView;
    }

    /**
     * 重定向 redirect前缀
     */
    @RequestMapping(value = "/testRedirect", method = RequestMethod.GET)
    public Object testRedirect(@RequestParam(value = "type", required = false) Integer type) {
        log.info("[testViewResolve] testRedirect. type={}", type);
        if (null == type) {
            //当前控制器根目录
            return "redirect:";
        } else if (type == 1) {
            //项目根URL目录
            return "redirect:/";
        } else if (type == 2) {
            //当前控制器testFtl
            return "redirect:testFtl";
        } else if (type == 3) {
            //当前控制器testJsp
            return "redirect:testJsp";
        } else if (type == 4) {
            ///app/login/loginIn控制器目录
            return "redirect:/app/login/loginIn";
        }
        return "redirect:";
    }

    /**
     * 重定向 forward前缀
     */
    @RequestMapping(value = "/testForward", method = RequestMethod.GET)
    public Object testForward(@RequestParam(value = "type", required = false) Integer type) {
        log.info("[testViewResolve] testRedirect. type={}", type);
        if (null == type) {
            //当前控制器根目录
            return "forward:";
        } else if (type == 1) {
            //项目根URL目录
            return "forward:/";
        } else if (type == 2) {
            //当前控制器testFtl
            return "forward:testFtl";
        } else if (type == 3) {
            //当前控制器testJsp
            return "forward:testJsp";
        } else if (type == 4) {
            ///app/login/loginIn控制器目录
            return "forward:/app/login/loginIn";
        }
        return "forward:";
    }


    /*
    redirect与forward的区别
    redirect方式相当 于"response.sendRedirect()".这种方式外部特征就是浏览器地址栏最后显示的路径是转发后的新的路径.工作方式是这样的，服务器端会首先发一个response给浏览器，然后浏览器收到这个response后再发一个requeset给服务器，然后服务器发新的response给浏览器。这时页面收到的request是一个新从浏览器发来的.这种方式的结果是：
    A.在转发前后有两个不同的request对象,转发前后的两个控制器在request上的参数(request.getParameter())和request属性(request.getAttribute())不能共享。
    B.如果转发前后的两个控制器都配置在spring 拦截器范围内,这样拦截器会拦截前后两个request,即会拦截两次。
    C.最后返回到浏览器后,因为地址栏显示的是转发后的url，所以刷新页面时只会执行后面的url映射的控制器。

    forward方式相当于 request.getRequestDispatcher().forward(request,response) .这种方式的外部特征是浏览器地址显示的路径是转发前的路径。工作方式是这样，forward 发生在服务器内部,在前一个控制器处理完毕后,直接进入下一个控制器处理，并将最后的response发给浏览器。这种方式的结果是：
    A.转发前后是同一个request,后一个控制器可共享前一个控制器的参数与属性。
    B.因为是同一个request，拦截器只会拦截前一个url，如果前一个url在映射时未配置到拦截器拦截，则拦截后一个url，即只拦截一次。
    C.最后返回到浏览器后,因为地址栏显示的是转发前的url，所以刷新页面时会依次执行前后两个控制器。
    */
}
