package com.qxd.birth.biz.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by xiangDong.qu on 16/2/2.
 */
@Component
@Slf4j
public class TestQuartz {
    public void execute(){
        //todo something
        log.info("[定时任务测试] I am quartz");
    }
}
