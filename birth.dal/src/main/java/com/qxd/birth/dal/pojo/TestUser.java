package com.qxd.birth.dal.pojo;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by xiangqong.qu on 16/9/20 08:47.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestUser {
    private String userName;
    private String userMobile;
}
