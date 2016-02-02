package templates.legend.dal
import com.tqmall.saint.dao.base.BaseDao;
import com.tqmall.saint.dao.common.MyBatisRepository;
import com.tqmall.saint.entity.sys.${FMT.XyzAbc($table)};

@MyBatisRepository
public interface ${FMT.XyzAbc($table)}Dao extends BaseDao<${FMT.XyzAbc($table)}> {

}
