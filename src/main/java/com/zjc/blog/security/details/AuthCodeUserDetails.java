package com.zjc.blog.security.details;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class AuthCodeUserDetails extends User {
    /**
     * user实体，用于暂存信息，便于登录状态保持
     */
    private com.zjc.blog.entity.User user;
    /**
     * 短信验证码
     */
    private String code;

    public AuthCodeUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthCodeUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public AuthCodeUserDetails(com.zjc.blog.entity.User user, String code, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
        this.code = code;
    }
}
