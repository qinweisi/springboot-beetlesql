package cn.com.qws.service.system;

import cn.com.qws.common.Constants;
import cn.com.qws.common.GetRequest;
import cn.com.qws.common.ResultJson;
import cn.com.qws.common.StringUtils;
import cn.com.qws.entity.system.Users;
import cn.com.qws.common.Constants;
import cn.com.qws.common.GetRequest;
import cn.com.qws.common.ResultJson;
import cn.com.qws.common.StringUtils;
import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 用户信息接口
 * @Author qinweisi
 * @Date 2019/7/19 09:19
 **/
@Service
public class UsersService {

    @Autowired
    private SQLManager sqlManager;

    private static final String SQL_ID = "system/users.";

    public ResultJson selectByTest() {
        ResultJson resultJson = new ResultJson();
        try {
            List<Users> list = sqlManager.select(SQL_ID + "selectByTest", Users.class);
            resultJson.setCode(Constants.ResultCode.SUCCESS);
            resultJson.setData(list);
            resultJson.setMsg(Constants.MsgCode.SUCCESS);
        }catch (Exception e){
            resultJson.setCode(Constants.ResultCode.FAIL);
            resultJson.setMsg(e.getMessage());
        }
        return resultJson;
    }

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
            return new ResultJson(Constants.ResultCode.FAIL,Constants.MsgCode.PASSWORD_IS_NOT_NULL,null);
        }
        // 用户名不能为空
        if (StringUtils.isEmpty(entity.getLoginName())) {
            return new ResultJson(Constants.ResultCode.FAIL,Constants.MsgCode.LOGIN_NAME_IS_NOT_NULL,null);
        }
        Users user = (Users) GetRequest.getSession().getAttribute("users");
        if (user != null){
            
        }
        try {
            // 保存
            sqlManager.insert(entity);
        } catch (Exception e) {
            // 异常返回
            return new ResultJson(Constants.ResultCode.FAIL,Constants.MsgCode.FAIL,null);
        }
        // 保存成功
        return new ResultJson(Constants.ResultCode.SUCCESS,Constants.MsgCode.SUCCESS,null);
    }

    /**
     * @Author qinweisi
     * @Description 根据登录名查询用户
     **/
    public Users queryByLoginName(String loginName){
        Map<String, Object> param = new HashMap<>();
        param.put("loginName",loginName);
        Users users = sqlManager.selectSingle(SQL_ID + "selectByLoginName",param,Users.class);
        return users;
    }

}
