package com.qxd.birth.common.proxy.staticProxy;

/**
 * Created by xiangdong.qu on 17/2/16 15:09.
 */

import com.qxd.birth.common.proxy.Subject;
import com.qxd.birth.common.proxy.SubjectImplOne;

/**
 * 该类用来代理 SubjectImplOne
 */
public class SubjectProxy implements Subject {

    private SubjectImplOne subjectImplOne;

    @Override
    public void sing() {
        if (null == subjectImplOne) {
            subjectImplOne = new SubjectImplOne();
        }
        subjectImplOne.sing();
    }

    @Override
    public String sing(String song) {
        return subjectImplOne.sing(song);
    }
}
