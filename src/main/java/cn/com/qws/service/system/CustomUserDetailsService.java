package cn.com.qws.service.system;


import cn.com.qws.common.GetRequest;
import cn.com.qws.entity.system.Menu;
import cn.com.qws.entity.system.Users;
import cn.com.qws.common.GetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 认证和授权
 * @Author qinweisi
 * @Date 2019/7/22 14:21
 **/
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersService  userService;

    @Autowired
    private MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        //--------------------认证账号
        Users users = userService.queryByLoginName(userName);
        if (users == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        HttpSession session = GetRequest.getSession();
        session.setAttribute("users", users);
        //-------------------开始授权
        List<Menu> menus = menuService.getMenusByUserId(users.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Menu menu : menus) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(menu.getUrl());
            //此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
            grantedAuthorities.add(grantedAuthority);
        }
        return new User(users.getLoginName(), users.getPassword(), true, true, true, true, grantedAuthorities);
    }
}
