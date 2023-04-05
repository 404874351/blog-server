package com.zjc.blog.security.handler;

import com.zjc.blog.enums.StateCodeMsg;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 动态权限决策管理器
 */
@Component
public class PermissionAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 获取jwt验证后的用户角色授权列表
        // 匿名用户，框架自动配置为ROLE_ANONYMOUS
        List<String> authenticationRoleList = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        // 检查请求路径允许的每一个角色，如与用户授权的角色之一匹配，则通过授权
        for (ConfigAttribute item : configAttributes) {
            if (authenticationRoleList.contains(item.getAttribute())) {
                return;
            }
        }
        // 授权未通过，则报错，交由AccessDeniedHandler处理
        throw new AccessDeniedException(StateCodeMsg.ACCESS_DENIED.getMsg());
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
