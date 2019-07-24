package cn.com.qws.dao.system;

import cn.com.qws.entity.system.Users;
import cn.com.qws.entity.system.Users;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description 用户信息接口
 * @Author qinweisi
 * @Date 2019/7/19 09:19
 **/
public interface UsersDao extends BaseMapper<Users> {

    public List<Users> selectByTest();

}
