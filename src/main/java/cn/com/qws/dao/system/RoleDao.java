package cn.com.qws.dao.system;

import cn.com.qws.entity.system.Role;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

/**
 * @Description 角色信息接口
 * @Author qinweisi
 * @Date 2019/7/19 09:19
 **/
@SqlResource("system/role")
public interface RoleDao extends BaseMapper<Role> {
	
}
