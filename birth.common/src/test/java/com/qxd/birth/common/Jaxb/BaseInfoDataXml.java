package com.qxd.birth.common.Jaxb;

import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xiangqong.qu on 16/10/8 11:21.
 */
@XmlRootElement
@ToString
public class BaseInfoDataXml {

    @Setter
    private String groupName;

    @Setter
    private String groupType;

    @XmlElement(name = "GName")
    public String getGroupName() {
        return groupName;
    }

    @XmlElement(name = "GType")
    public String getGroupType() {
        return groupType;
    }

}
