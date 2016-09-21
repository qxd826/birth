package com.qxd.birth.web.web.insurance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by xiangqong.qu on 16/9/21 19:39.
 */
@Controller
@Slf4j
@RequestMapping(value = "/insurance")
public class InsuranceController {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public void testInsurance(HttpServletRequest request) {
        try {
            BufferedReader bufferedReader = request.getReader();
            String str = "";
            String result = "";
            while ((str = bufferedReader.readLine()) != null) {
                result += str;
            }
            return;
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
