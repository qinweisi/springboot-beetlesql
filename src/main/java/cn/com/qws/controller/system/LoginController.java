package cn.com.qws.controller.system;

import cn.com.qws.common.Constants;
import cn.com.qws.common.ResultJson;
import cn.com.qws.common.StringUtils;
import cn.com.qws.conf.auth.JwtHelper;
import cn.com.qws.entity.system.Users;
import cn.com.qws.exception.RestExceptionHandler;
import cn.com.qws.service.system.UsersService;
import cn.com.qws.common.Constants;
import cn.com.qws.common.ResultJson;
import cn.com.qws.common.StringUtils;
import cn.com.qws.conf.auth.JwtHelper;
import cn.com.qws.entity.system.Users;
import cn.com.qws.exception.RestExceptionHandler;
import cn.com.qws.service.system.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 用户信息控制器
 * @Author qinweisi
 * @Date 2019/7/19 09:19
 **/
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * @Author qinweisi
     * @Description 注册
     **/
    @RequestMapping("/register")
    @ResponseBody
    public ResultJson register(Users entity) throws Exception {
        return usersService.save(entity);
    }

    /**
     * @Author qinweisi
     * @Description 登录
     **/
    @RequestMapping("/in")
    @ResponseBody
    public ResultJson login(Users entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        ResultJson resultJson = new ResultJson();
        if(entity == null){
            return RestExceptionHandler.retParam(1100, Constants.MsgCode.USER_IS_NULL);
        }
        String username= entity.getLoginName();
        String password= entity.getPassword();
        if (StringUtils.isEmpty(username)){
            return RestExceptionHandler.retParam(1101,Constants.MsgCode.LOGIN_NAME_IS_NOT_NULL);
        }
        if (StringUtils.isEmpty(password)){
            return RestExceptionHandler.retParam(1102,Constants.MsgCode.PASSWORD_IS_NOT_NULL);
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authRequest);
        String name = authenticate.getName();
        Users userInfo = usersService.queryByLoginName(username);
        Map<String, Object> map = new HashMap<String, Object>();
        String token = JwtHelper.createJWT((User) authenticate.getPrincipal(), 24 * 60 * 60 * 1000, "qmhd@2018");
        map.put("token", token);
        if (userInfo != null) {
            map.put("username", userInfo.getName());
        }
        response.setHeader("Authorization", "Bearer " + token);
        String header = response.getHeader("Authorization");
        System.out.println(header);
//        resultJson = new ResultJson(Constants.ResultCode.SUCCESS,"登录成功",map);
        return new ResultJson(Constants.ResultCode.SUCCESS,"登录成功",map);
    }

    @RequestMapping("/error")
    @ResponseBody
    public ResultJson loginError(){
        ResultJson resultJson = new ResultJson(400,"登录失败",null);
        return resultJson;
    }
}

