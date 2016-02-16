package com.qxd.birth.dal.entity.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by xiangDong.qu on 16/2/16.
 */
@EqualsAndHashCode
@Data
public abstract class AppBaseEntity extends IdEntity {
    //APP版本号
    protected String ver;
    //数据来源 1.Android 2.ios
    protected String refer;
    //手机系统
    protected String sys;
    //token用户信息
    protected String token;
    //签名信息
    protected String sign;
    //设备id
    protected String deviceId;
    //手机品牌
    protected String phoneBrand;
    //工作网络
    protected String networkType;
}
