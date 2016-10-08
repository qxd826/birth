package com.qxd.birth.common.Jaxb;

import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xiangqong.qu on 16/10/8 10:51.
 */
@XmlRootElement(name = "Group")
@ToString
public class GroupDataXml {

    @Setter
    private BaseInfoDataXml baseInfoDataXml;

    @Setter
    private MemberInfoDataXml memberInfoDataXml;

    @XmlElement(name = "BaseInfo")
    public BaseInfoDataXml getBaseInfoDataXml() {
        return baseInfoDataXml;
    }

    @XmlElement(name = "MemberInfo")
    public MemberInfoDataXml getMemberInfoDataXml() {
        return memberInfoDataXml;
    }
}
