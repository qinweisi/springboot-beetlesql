package cn.com.qws.dao.system;

import cn.com.qws.entity.system.Menu;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description 菜单接口
 * @Author qinweisi
 * @Date 2019/7/19 09:19
 **/
@SqlResource("system/menu")
public interface MenuDao extends BaseMapper<Menu> {

    public List<Menu> findAll();

    public List<Menu> findByState(@Param("state") Integer state);

}
