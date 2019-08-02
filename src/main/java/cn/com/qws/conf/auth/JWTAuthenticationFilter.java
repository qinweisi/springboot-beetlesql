package cn.com.qws.conf.auth;

import cn.com.qws.common.Constants;
import cn.com.qws.entity.system.Users;
import cn.com.qws.utils.RedisUtil;
import cn.com.qws.utils.ResponseUtil;
import cn.com.qws.utils.SpringContextUtils;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * token的校验 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private final PathMatcher pathMatcher = new AntPathMatcher();

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        String uri = request.getRequestURI();
        if (header == null || !header.startsWith("Bearer ")) {
            if (isWhiteURL(uri)) {
                chain.doFilter(request, response);
            } else {
                ResponseUtil.GoResponseSystemException(response, 999, "权限不足");
            }
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(response, header);
        if (authentication == null) {
            ResponseUtil.GoResponseSystemException(response, 906, "token已过期或失效");
            return;
        } else {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    /**
     * @Author qinweisi
     * @Description 验证token,用户名/密码
     **/
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletResponse response, String token) {
        Claims claims = null;
        try {
            claims = JwtHelper.parseJWT(token.replace("Bearer ", ""), "qmhd@2018");
        } catch (Exception e) {
            ResponseUtil.GoResponseSystemException(response, 903, "验证token异常");
            return null;
        }
        if (claims != null) {
            // 获取用户信息
            String user = claims.getSubject();
            Users userInfo = JSON.toJavaObject(JSON.parseObject(user), Users.class);
            if (user == null) {
                return null;
            }
            // 由于注入RedisUtil为null，因此采用 获取上下文实例对象 的方式获取实例，如果有更好的实现方法，请修改
            RedisUtil redisUtil = SpringContextUtils.getBean(RedisUtil.class);
            // 查询redis中的token
            Object o = redisUtil.get(Constants.REDIS_USER_TOKEN_KEY + userInfo.getId());
            if (token != null) {
                String rtoken = (String) o;
                // 判断当前token是否和redis中相同
                if (!rtoken.equals(token)) {
                    // 不相同抛出异常
                    return null;
                }
                return new UsernamePasswordAuthenticationToken(userInfo.getLoginName(), null, new ArrayList<MenuGrantedAuthority>());
            }
        }
        return null;
    }

    /**
     * @Author qinweisi
     * @Description 判断url是否在白名单内
     **/
    private boolean isWhiteURL(String currentURL) {
        for (String whiteURL : Constants.AUTH_WHITELIST) {
            if (pathMatcher.match(whiteURL, currentURL)) {
                logger.info("当前url: " + currentURL + "在白名单内");
                return true;
            }
            logger.info("当前url: " + currentURL + "不在白名单内");
        }
        return false;
    }

}
