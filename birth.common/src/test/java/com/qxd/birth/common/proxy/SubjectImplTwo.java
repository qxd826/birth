package com.qxd.birth.common.proxy;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * Created by xiangdong.qu on 17/2/16 16:26.
 */
@Slf4j
public class SubjectImplTwo implements Subject {

    @Override
    public void sing() {
        log.info("SubjectImplTwo >>>>>>>  啦啦啦啦啦啦");
    }

    @Override
    public String sing(String song) {
        return MessageFormat.format("SubjectImplTwo >>>>>>> {0}", song);
    }
}
