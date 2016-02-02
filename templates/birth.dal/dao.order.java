package templates.legend.dal

import com.tqmall.legend.dao.base.BaseDao;
import com.tqmall.legend.dao.common.MyBatisRepository;
import com.tqmall.legend.entity.order.${FMT.XyzAbc($table)};

@MyBatisRepository
public interface ${FMT.XyzAbc($table)}Dao extends BaseDao<${FMT.XyzAbc($table)}> {

}
