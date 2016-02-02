package templates.legend.biz
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tqmall.saint.biz.base.BaseServiceImpl;
import com.tqmall.saint.dao.customer.${FMT.XyzAbc($table)}Dao;
import com.tqmall.saint.entity.customer.${FMT.XyzAbc($table)};

@Service
public class ${FMT.XyzAbc($table)}Service extends BaseServiceImpl {

  @Autowired
  private ${FMT.XyzAbc($table)}Dao ${FMT.xyzAbc($table)}Dao;

  public List<${FMT.XyzAbc($table)}> getAll${FMT.XyzAbc($table)}() {
    return super.getAll(${FMT.xyzAbc($table)}Dao);
  }

  public Page<${FMT.XyzAbc($table)}> getPage${FMT.XyzAbc($table)}(Pageable pageable, Map<String, Object> searchParams) {
    return super.getPage(${FMT.xyzAbc($table)}Dao, pageable, searchParams);
  }

  public ${FMT.XyzAbc($table)} get${FMT.XyzAbc($table)}ById(Long id) {
    return super.getById(${FMT.xyzAbc($table)}Dao, id);
  }

  public boolean save${FMT.XyzAbc($table)}(${FMT.XyzAbc($table)} ${FMT.xyzAbc($table)}) {
    return super.save(${FMT.xyzAbc($table)}Dao, ${FMT.xyzAbc($table)});
  }

  public boolean delete${FMT.XyzAbc($table)}ById(Long id) {
    return super.deleteById(${FMT.xyzAbc($table)}Dao, id);
  }

  public int delete${FMT.XyzAbc($table)}ByIds(Long[] ids) {
    return super.deleteByIds(${FMT.xyzAbc($table)}Dao, ids);
  }
}
