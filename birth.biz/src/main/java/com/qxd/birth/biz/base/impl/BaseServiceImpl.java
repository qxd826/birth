package com.qxd.birth.biz.base.impl;

import com.qxd.birth.biz.base.BaseService;
import com.qxd.birth.biz.common.DefaultPage;
import com.qxd.birth.dal.dao.base.BaseDao;
import com.qxd.birth.dal.entity.base.BaseEntity;
import com.qxd.birth.dal.entity.base.IdEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangDong.qu on 16/2/2.
 */
public abstract class BaseServiceImpl implements BaseService {

    @Override
    public <T extends IdEntity> Page<T> getPage(BaseDao<T> baseDao, Pageable pageable, Map<String, Object> searchParams) {
        if (pageable.getSort() != null) {
            Iterator<Sort.Order> iterator = pageable.getSort().iterator();
            ArrayList<String> sorts = new ArrayList<String>();
            while (iterator.hasNext()) {
                Sort.Order order = iterator.next();
                sorts.add(order.getProperty() + " " + order.getDirection().name());
            }
            searchParams.put("sorts", sorts);
        }

        Integer totalSize = baseDao.selectCount(searchParams);

        PageRequest pageRequest = new PageRequest((pageable.getPageNumber() < 1 ? 1 : pageable.getPageNumber()) - 1, pageable.getPageSize() < 1 ? 1 : pageable.getPageSize(), pageable.getSort());

        searchParams.put("limit", pageRequest.getPageSize());
        searchParams.put("offset", pageRequest.getOffset());

        List<T> data = baseDao.select(searchParams);
        DefaultPage<T> page = new DefaultPage<T>(data, pageRequest, totalSize);
        return page;
    }


    @Override
    public <T extends IdEntity> List<T> getAll(BaseDao<T> baseDao) {
        return baseDao.select(null);
    }

    @Override
    public <T extends IdEntity> T getById(BaseDao<T> baseDao, Object id) {
        return baseDao.selectById(id);
    }

    @Override
    public <T extends IdEntity> boolean save(BaseDao<T> baseDao, T entity) {
        if (entity instanceof BaseEntity) {
            ((BaseEntity) entity).setDefaultBizValue();
        }
        if (entity.getId() == null) {
            return baseDao.insert(entity) > 0;
        } else {
            return baseDao.updateById(entity) > 0;
        }
    }

    @Override
    public <T extends IdEntity> boolean deleteById(BaseDao<T> baseDao, Object id) {
        T t = baseDao.selectById(id);
        if (t == null) {
            return false;
        }

        return baseDao.deleteById(id) > 0;
    }

    @Transactional
    @Override
    public <T extends IdEntity> int deleteByIds(BaseDao<T> baseDao, Object[] ids) {
        if (ids.length == 0) {
            return 0;
        }
        return baseDao.deleteByIds(ids);
    }


    public <T extends IdEntity> List<T> select(BaseDao<T> baseDao, Map<String, Object> searchParams) {
        return baseDao.select(searchParams);
    }

    @Transactional
    public <T extends IdEntity> int batchInsert(BaseDao<T> baseDao, List<T> list, Integer MAX_SIZE) {
        if (MAX_SIZE == null) {
            MAX_SIZE = 300;
        }
        int totalSize = list.size();
        int size = totalSize % MAX_SIZE == 0 ? totalSize / MAX_SIZE : totalSize / MAX_SIZE + 1;
        for (int i = 0; i < size; i++) {
            if (i + 1 == size) {
                baseDao.batchInsert(list.subList(i * MAX_SIZE, totalSize));
            } else {
                baseDao.batchInsert(list.subList(i * MAX_SIZE, i * MAX_SIZE + MAX_SIZE));
            }
        }
        return totalSize;
    }
}
