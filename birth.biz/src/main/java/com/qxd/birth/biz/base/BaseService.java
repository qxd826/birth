package com.qxd.birth.biz.base;

import com.qxd.birth.dal.dao.base.BaseDao;
import com.qxd.birth.dal.entity.base.IdEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by xiangDong.qu  on 16/2/2.
 */
public interface BaseService {

    public <T extends IdEntity> List<T> getAll(BaseDao<T> baseDao);

    public <T extends IdEntity> Page<T> getPage(BaseDao<T> baseDao, Pageable pageable, Map<String, Object> parameters);

    public <T extends IdEntity> T getById(BaseDao<T> baseDao, Object id);

    public <T extends IdEntity> boolean save(BaseDao<T> baseDao, T entity);

    public <T extends IdEntity> boolean deleteById(BaseDao<T> baseDao, Object id);

    public <T extends IdEntity> int deleteByIds(BaseDao<T> baseDao, Object[] ids);

    public <T extends IdEntity> List<T> select(BaseDao<T> baseDao, Map<String, Object> parameters);

    public <T extends IdEntity> int batchInsert(BaseDao<T> baseDao, List<T> list, Integer maxSize);

}
