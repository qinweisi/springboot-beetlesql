package cn.com.qws.service.system;

import cn.com.qws.common.*;
import cn.com.qws.entity.system.Users;
import cn.com.qws.service.base.BaseService;
import cn.com.qws.utils.StringUtils;
import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 用户信息接口
 * @Author qinweisi
 * @Date 2019/7/19 09:19
 **/
@Service
public class UsersService extends BaseService {

    @Autowired
    private SQLManager sqlManager;

    private static final String SQL_ID = "system/users.";

    /**
     * @Author qinweisi
     * @Description 保存
     **/
    public ResultJson save(Users entity) {
        // 密码加密
        if (!StringUtils.isEmpty(entity.getPassword())) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        } else {
            // 密码不能为空
            return new ResultJson(Constants.ResultCode.FAIL, Constants.MsgCode.PASSWORD_IS_NOT_NULL, null);
        }
        // 用户名不能为空
        if (StringUtils.isEmpty(entity.getLoginName())) {
            return new ResultJson(Constants.ResultCode.FAIL, Constants.MsgCode.LOGIN_NAME_IS_NOT_NULL, null);
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("loginName", entity.getLoginName());
            Users users = sqlManager.selectSingle(SQL_ID + "selectByLoginName", params, Users.class);
            if (users != null) {
                return new ResultJson(Constants.ResultCode.FAIL, "用户名已存在", null);
            }
        }
        Users user = (Users) GetRequest.getSession().getAttribute("users");
        if (user != null) {
            entity.setCreateUser(user.getId());
            entity.setUpdateUser(user.getId());
        }
        try {
            // 保存
            sqlManager.insert(entity);
        } catch (Exception e) {
            // 异常返回
            return new ResultJson(Constants.ResultCode.FAIL, Constants.MsgCode.FAIL, null);
        }
        // 保存成功
        return new ResultJson(Constants.ResultCode.SUCCESS, Constants.MsgCode.SUCCESS, null);
    }

    /**
     * @Author qinweisi
     * @Description 根据登录名查询用户
     **/
    public Users queryByLoginName(String loginName) {
        Map<String, Object> params = new HashMap<>();
        params.put("loginName", loginName);
        Users users = sqlManager.selectSingle(SQL_ID + "selectByLoginName", params, Users.class);
        return users;
    }

    /**
     * @Author qinweisi
     * @Description 分页
     **/
    public ResultJson page(Users entity, Integer pageIndex, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("entity", entity);
        DataGridOptions options = new DataGridOptions(pageSize, 1, pageIndex);
        options.setParams(params);
        PagerModel model = findPagerModel(SQL_ID + "queryPage", options, Users.class);
        return new ResultJson(Constants.ResultCode.SUCCESS, Constants.MsgCode.SUCCESS, model);
    }

    /**
     * @Author qinweisi
     * @Description 根据用户ID查询用户
     **/
    public ResultJson queryById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Users users = sqlManager.selectSingle(SQL_ID + "findByCondition", params, Users.class);
        return new ResultJson(Constants.ResultCode.SUCCESS, Constants.MsgCode.SUCCESS, users);
    }

    /**
     * @Author qinweisi
     * @Description 修改
     **/
    public ResultJson update(Users entity) {
        entity.setPassword(null);
        entity.setLoginName(null);
        Users user = (Users) GetRequest.getSession().getAttribute("users");
        Map<String, Object> params = new HashMap<>();
        params.put("entity",entity);
        if (user != null) {
            entity.setUpdateUser(user.getId());
        }
        try {
            // 修改
            sqlManager.update(SQL_ID + "updateById",params);
        } catch (Exception e) {
            // 异常返回
            return new ResultJson(Constants.ResultCode.FAIL, Constants.MsgCode.FAIL, null);
        }
        // 修改成功
        return new ResultJson(Constants.ResultCode.SUCCESS, Constants.MsgCode.SUCCESS, null);
    }

}
