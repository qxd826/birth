package com.qxd.birth.common.Jaxb;

import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by xiangqong.qu on 16/10/8 11:30.
 */
@XmlRootElement
@ToString
public class PeopleListXml {

    @Setter
    private List<PeopleDataXml> peopleDataXmlList;

    @XmlElement(name = "PeopleData")
    public List<PeopleDataXml> getPeopleDataXmlList() {
        return peopleDataXmlList;
    }
}
