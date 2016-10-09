package com.qxd.birth.dal.entity.master.user;

import com.qxd.birth.dal.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by xiangDong.qu on 16/2/17.
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class User extends BaseEntity {

    private String name;//用户名
    private String mobile;//用户电话号码

    private Boolean die; //是否死亡
}
