package com.qxd.birth.dal.entity.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public abstract class BaseEntity extends IdEntity {

  /**
   * isDeleted字段是否删除，标记为Y，已删除
   */
  public static final String IS_DELETE_ENABLE = "Y";

  /**
   * isDeleted字段是否删除，标记为N，没删除
   */
  public static final String IS_DELETE_UNENABLE = "N";

  protected String isDeleted;
  protected Date gmtCreate;
  protected Long creator;
  protected Date gmtModified;
  protected Long modifier;

  public void setDefaultBizValue() {
    if (id == null) {
      // 创建
      if (gmtCreate == null) {
        gmtCreate = new Date();
      }
      if (creator == null) {
        creator = 1L;
      }
      if (isDeleted == null) {
        isDeleted = IS_DELETE_UNENABLE;
      }
    }
    // 修改
    if (modifier == null) {
      modifier = 1L;
    }
    gmtModified = new Date();

  }

}
