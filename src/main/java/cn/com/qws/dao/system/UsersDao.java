package cn.com.qws.dao.system;

import cn.com.qws.entity.system.Users;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description 用户信息接口
 * @Author qinweisi
 * @Date 2019/7/19 09:19
 **/
@SqlResource("system/user")
public interface UsersDao extends BaseMapper<Users> {

    public List<Users> selectByTest();

}
