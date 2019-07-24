package cn.com.qws.conf.auth;

import cn.com.qws.dao.system.MenuDao;
import cn.com.qws.entity.system.Menu;
import cn.com.qws.service.system.MenuService;
import cn.com.qws.entity.system.Menu;
import cn.com.qws.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author lianyc
 */
@Service
public class JWTFilterInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private MenuService menuService;


    @SuppressWarnings("unused")
    private HashMap<String, Collection<ConfigAttribute>> resourceMap = null;

    @PostConstruct
    public void loadResourceDefine() {
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        System.out.println("---------------------> 开始初始化权限数据 <---------------------");
        ConfigAttribute cfg;
        List<Menu> permissions = menuDao.findByState(1);
        for (Menu permission : permissions) {
            Collection<ConfigAttribute> array = new ArrayList<>();
            cfg = new SecurityConfig(permission.getName());
            // 此处只添加了权限的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。
            // 此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            array.add(cfg);
            // 用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value
            resourceMap.put(permission.getUrl(), array);
        }
        System.out.println("-----------------初始化系统权限数据完成---------------");
    }

    // 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，
    // 用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (resourceMap == null)
            loadResourceDefine();
        // object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for (Iterator<String> iter = resourceMap.keySet().iterator(); iter.hasNext();) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return resourceMap.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
