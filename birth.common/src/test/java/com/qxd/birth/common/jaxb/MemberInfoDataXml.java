package com.qxd.birth.common.jaxb;

import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xiangqong.qu on 16/10/8 11:26.
 */
@XmlRootElement
@ToString
public class MemberInfoDataXml {

    @Setter
    private PeopleListXml peopleListXml;

    @Setter
    private Integer peopleNum;

    @XmlElement(name = "PeopleList")
    public PeopleListXml getPeopleListXml() {
        return peopleListXml;
    }

    @XmlElement(name = "PeopleNum")
    public Integer getPeopleNum() {
        return peopleNum;
    }
}
