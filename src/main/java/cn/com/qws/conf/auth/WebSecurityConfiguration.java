package cn.com.qws.conf.auth;

import cn.com.qws.service.system.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * SpringSecurity的配置
 * 通过SpringSecurity的配置，将JWTLoginFilter，JWTAuthenticationFilter组合在一起
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法权限控制
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * 需要放行的URL
     */
    private static final String[] AUTH_WHITELIST = {
            // -- register url
            "/login*",
            "/gen",
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/hardware/**",
            "/**",
            "/app/**",
            "/data/download*"
            // other public endpoints of your API may be appended to this array
    };

//    @Autowired
//    private UserDetailsService userDetailsService;
    @Autowired
    private CustomUserDetailsService userDetailsService;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private MySecurityInterceptor myFilterSecurityInterceptor;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return new RestAuthenticationAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated().and().
                addFilter(new JWTAuthenticationFilter(authenticationManager())).logout().permitAll()
                .and().cors().and().csrf().disable();
    }

    // 该方法是登录的时候会进入
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        // 使用自定义身份验证组件
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder); // user Details Service验证;
    }


    @Bean
    public CacheManager cacheNabger() {
        return null;
    }

}
