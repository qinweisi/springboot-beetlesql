package cn.com.qws.service.base;

import cn.com.qws.common.DataGridOptions;
import cn.com.qws.common.PagerModel;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseService {

    @Autowired
    private SQLManager sqlManager;


    public int insert( Object o) {
        return sqlManager.insert(o);

    }

    public <T> int insert(List<T> o, Class clazz) {
        sqlManager.insertBatch(clazz,o);
        return 0;
    }

    public  int update(Object o) {
        return sqlManager.updateById(o);
    }


    public int update(String sqlId, Map<String,Object> params) {
        return sqlManager.update(sqlId, params);
    }

    public int update(String table, String pkid, Map<String, Object> parmas) {

        List<Object> values = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("update ").append(table).append(" ");
        sb.append("set ");
        int size = parmas.size();
        int count = 0;
        for (Map.Entry<String,Object> entry:parmas.entrySet()){
            sb.append(entry.getKey()).append("=").append("?");
            count++;
            if (count!=size){
                sb.append(",");
            }
            else {
                sb.append(" ");
            }
            values.add(entry.getValue());
        }
        sb.append("where id=?");
        values.add(pkid);
        SQLReady sqlReady = new SQLReady(sb.toString(), (Object[])values.toArray(new Object[size]));

        int rs = sqlManager.executeUpdate(sqlReady);
        return rs;
    }


    public int delete(Class clazz, String id) {
        return sqlManager.deleteById(clazz, id);
    }

    public <T> T findById(Class<T> clazz, String id) {
        return sqlManager.unique(clazz,id);
    }

    public <T> List<T> findBySqlId(Class<T> clazz, String sqlId,Map<String,Object> params) {
        return sqlManager.select(sqlId,clazz,params);
    }

    public <T> T unique(Class<T> clazz, String sqlId, Map<String, Object> params) {
        List<T> ts = sqlManager.select(sqlId,clazz,params);
        if (ts.size()>0){
            return ts.get(0);
        }
        return null;
    }

    public <T> T unique(Class<T> clazz, String sqlId) {
        return unique(clazz,sqlId,new HashMap<String,Object>());
    }

    public int executeSql(String sql,Object ...values) {

        return sqlManager.executeUpdate(new SQLReady(sql,values));
    }

    public long count(Map<String,Object> params) {
        return sqlManager.selectSingle("base.count",params,Long.class);

    }

    public PagerModel findPagerModel(String selectId, DataGridOptions options, Class clazz) {
        PageQuery query = ServiceUtil.pageToQuery(options);
        sqlManager.pageQuery(selectId, clazz,query);
        return ServiceUtil.queryToTable(query);
    }

}
