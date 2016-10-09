package com.qxd.birth.web.web.httpClient;

import com.qxd.birth.common.utils.HttpClientUtil;
import com.qxd.birth.dal.entity.master.user.User;
import com.qxd.birth.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangqong.qu on 16/10/9 11:10.
 */
@Controller
@Slf4j
@RequestMapping(value = "/httpClient")
public class TestHttpClientUseController extends BaseController {

    /**
     * 测试httpClientPost请求
     *
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "/getPost", method = RequestMethod.POST)
    @ResponseBody
    public Object getPost(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = (String) enumeration.nextElement();
            String paramValue = request.getParameter(paramName);
            if (StringUtils.isBlank(paramValue)) {
                continue;
            }
            paramMap.put(paramName, paramValue);
        }
        log.info("[httpclient get test] param:{}", paramMap);
        return paramMap;
    }

    /**
     * 测试 httpClient post请求
     *
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "/testPost", method = RequestMethod.GET)
    @ResponseBody
    public Object sendPost(HttpServletRequest request) {
        User user = new User();
        user.setName("qxd");
        user.setMobile("15158116453");
        user.setId(1L);

        String result = null;
        try {
            result = HttpClientUtil.sendData(user, "http://localhost:8088/birth/httpClient/getPost", "UTF-8");
        } catch (Exception e) {
            log.error("httpclient post 测试失败", e);
        }
        return result;
    }
}
