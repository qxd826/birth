package com.qxd.birth.common.Jaxb;

import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xiangqong.qu on 16/10/8 11:30.
 */
@XmlRootElement
@ToString
public class PeopleDataXml {

    @Setter
    private String name;

    @Setter
    private Integer age;

    @XmlElement(name = "PName")
    public String getName() {
        return name;
    }

    @XmlElement(name = "PAge")
    public Integer getAge() {
        return age;
    }
}
