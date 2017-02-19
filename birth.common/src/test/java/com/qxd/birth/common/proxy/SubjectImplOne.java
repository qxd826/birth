package com.qxd.birth.common.proxy;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * Created by xiangdong.qu on 17/2/16 15:07.
 */
@Slf4j
public class SubjectImplOne implements Subject {

    @Override
    public void sing() {
        log.info("SubjectImplOne >>>>>>>   啦啦啦啦啦啦");
    }

    @Override
    public String sing(String song) {
        return MessageFormat.format("SubjectImplOne >>>>>>> {0}", song);
    }
}
