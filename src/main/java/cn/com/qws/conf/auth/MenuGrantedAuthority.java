package cn.com.qws.conf.auth;

import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public class MenuGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 6871727498260208564L;
    private String name;
    private String url;
    private Set<MenuGrantedAuthority> children = new HashSet<>();

    @Override
    public String getAuthority() {

        return url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<MenuGrantedAuthority> getChildren() {
        return children;
    }

    public void setChildren(Set<MenuGrantedAuthority> children) {
        this.children = children;
    }

}
