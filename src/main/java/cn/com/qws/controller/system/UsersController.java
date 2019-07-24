package cn.com.qws.controller.system;

import cn.com.qws.service.system.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 用户信息控制器
 * @Author qinweisi
 * @Date 2019/7/19 09:19
 **/
@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;




}

