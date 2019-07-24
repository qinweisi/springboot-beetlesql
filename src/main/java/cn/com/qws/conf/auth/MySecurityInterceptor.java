package cn.com.qws.conf.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

/**
 * @author lianyc
 */
@Service
public class MySecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private JWTFilterInvocationSecurityMetadataSourceService securityMetadataSource;
    @Autowired
    private JWTAccessDecisionManager accessDecisionManager;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostConstruct
    public void init() {
        System.out.println("---CustomSecurityInterceptor init method---");
        super.setAccessDecisionManager(accessDecisionManager);
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    /**
     * @Title invoke
     * @author lianyc 2017年5月16日
     * @Description
     */
    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

}
