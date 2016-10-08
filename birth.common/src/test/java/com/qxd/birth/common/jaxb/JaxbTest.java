package com.qxd.birth.common.jaxb;

import com.qxd.birth.common.BaseTest;
import com.qxd.birth.common.utils.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangqong.qu on 16/10/8 14:31.
 */
@Slf4j
public class JaxbTest extends BaseTest {

    @Before
    public void before() {

    }

    @After
    public void after() {

    }

    @Test
    public void testJaxb() {
        PeopleListXml peopleListXml = new PeopleListXml();
        List<PeopleDataXml> peopleDataXmlList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            PeopleDataXml peopleDataXml = new PeopleDataXml();
            peopleDataXml.setName("我是" + i);
            peopleDataXml.setAge(i);
            peopleDataXmlList.add(peopleDataXml);
        }

        peopleListXml.setPeopleDataXmlList(peopleDataXmlList);

        BaseInfoDataXml baseInfoDataXml = new BaseInfoDataXml();
        baseInfoDataXml.setGroupName("分组一");
        baseInfoDataXml.setGroupType("分类一");

        MemberInfoDataXml memberInfoDataXml = new MemberInfoDataXml();
        memberInfoDataXml.setPeopleNum(3);
        memberInfoDataXml.setPeopleListXml(peopleListXml);

        GroupDataXml groupDataXml = new GroupDataXml();
        groupDataXml.setBaseInfoDataXml(baseInfoDataXml);
        groupDataXml.setMemberInfoDataXml(memberInfoDataXml);

        String result = XmlUtil.dataToXml(groupDataXml, GroupDataXml.class);


        GroupDataXml groupDataXml1 = XmlUtil.dataFromXml(result, GroupDataXml.class);

        log.info("test jaxb to xml:{}", result);
        log.info("test jaxb from xml:{}", groupDataXml1);
    }

}
