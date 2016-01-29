package com.qxd.birth.web.base;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiangDong.qu on 16/1/29.
 */
public abstract class BaseController {

    @Autowired
    protected HttpServletRequest request;
}
