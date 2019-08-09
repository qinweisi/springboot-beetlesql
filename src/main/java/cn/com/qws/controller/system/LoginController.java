package cn.com.qws.controller.system;

import cn.com.qws.common.Constants;
import cn.com.qws.common.GetRequest;
import cn.com.qws.common.ResultJson;
import cn.com.qws.conf.auth.JwtHelper;
import cn.com.qws.dto.system.LoginDto;
import cn.com.qws.entity.system.Users;
import cn.com.qws.exception.RestExceptionHandler;
import cn.com.qws.service.system.UsersService;
import cn.com.qws.utils.ConvertUtils;
import cn.com.qws.utils.RedisUtil;
import cn.com.qws.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 用户信息控制器
 * @Author qinweisi
 * @Date 2019/7/19 09:19
 **/
@RestController
@RequestMapping("/login")
@Api(tags = "登录管理接口")
public class LoginController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * @Author qinweisi
     * @Description 注册
     **/
    @PostMapping("register")
    @ResponseBody
    @ApiOperation("注册")
    public ResultJson register(@RequestBody LoginDto dto) throws Exception {
        Users users = ConvertUtils.sourceToTarget(dto,Users.class);
        return usersService.save(users);
    }

    /**
     * @Author qinweisi
     * @Description 登录
     **/
    @GetMapping("in")
    @ResponseBody
    @ApiOperation("登录")
    public ResultJson login(LoginDto entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
//        String name = authenticate.getName();
        Users userInfo = usersService.queryByLoginName(username);
        Map<String, Object> map = new HashMap<String, Object>();// 24 * 60 * 60 * 1000
        String token = JwtHelper.createJWT((User) authenticate.getPrincipal(), 24 * 60 * 60 * 1000, Constants.SYSTEM_TOKEN_KEY, JSON.toJSONString(userInfo));
        map.put("token", token);
        if (userInfo != null) {
            map.put("username", userInfo.getName());
        }
        response.setHeader("Authorization", "Bearer " + token);
        String header = response.getHeader("Authorization");
        System.out.println(header);
        // 将token存在redis
        redisUtil.set(Constants.REDIS_USER_TOKEN_KEY + userInfo.getId(), header);
        System.out.println(redisUtil.get(Constants.REDIS_USER_TOKEN_KEY + userInfo.getId()));
        return new ResultJson(Constants.ResultCode.SUCCESS,"登录成功",map);
    }

    /**
     * @Author qinweisi
     * @Description 登出
     **/
    @GetMapping("out")
    @ResponseBody
    @ApiOperation("登出")
    public ResultJson loginOut()throws Exception{
        Users users = (Users) GetRequest.getSession().getAttribute("users");
        // 删除token
        redisUtil.remove(Constants.REDIS_USER_TOKEN_KEY + users.getId());
        // 删除session中的用户信息
        GetRequest.getSession().removeAttribute("users");
        return new ResultJson(Constants.ResultCode.SUCCESS,Constants.MsgCode.SUCCESS,null);
    }

}

