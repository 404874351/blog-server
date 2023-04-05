package com.zjc.blog.security.filter;

import cn.hutool.core.collection.CollUtil;
import com.zjc.blog.dto.PermissionRoleDto;
import com.zjc.blog.entity.BaseEntity;
import com.zjc.blog.entity.Permission;
import com.zjc.blog.entity.Role;
import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.exception.TokenException;
import com.zjc.blog.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.zjc.blog.security.filter.JwtAuthenticationFilter.TOKEN_STATE_NAME;

/**
 * 动态接口权限过滤器，解析路径并获取其授权的角色
 * 白名单路径，将不通过此处
 */
@Component
public class PermissionAuthorizationFilter implements FilterInvocationSecurityMetadataSource {
    /**
     * 全局权限信息列表
     */
    public static List<PermissionRoleDto> permissionRoleDtoList;

    /**
     * 路径匹配器
     */
    public static AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private PermissionService permissionService;

    /**
     * 在注入后加载权限信息
     */
    @PostConstruct
    public void loadDataSource() {
        permissionRoleDtoList = permissionService.permissionRoleDtoList();
    }

    /**
     * 清空权限信息，在权限被修改后调用
     */
    public void clearDataSource() {
        permissionRoleDtoList = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 如权限信息修改后被情况，则下次访问重新加载
        if (permissionRoleDtoList == null) {
            loadDataSource();
        }
        FilterInvocation fi = (FilterInvocation) object;
        // jwt过滤器将再此之前执行
        // 对于token过期、非法、失效等情况，jwt过滤器将不会直接导向EntryPoint，需要再进行一次
        StateCodeMsg tokenState = (StateCodeMsg) fi.getRequest().getAttribute(TOKEN_STATE_NAME);
        if (tokenState != null && !tokenState.equals(StateCodeMsg.TOKEN_NOT_EXIST)) {
            throw new TokenException(tokenState);
        }

        // 获取请求Url
        String url = fi.getRequest().getRequestURI();
        // 检查每一个接口权限，寻找与当前请求路径匹配的对象
        for (PermissionRoleDto permissionRoleDto : permissionRoleDtoList) {
            if (antPathMatcher.match(permissionRoleDto.getUrl(), url)
                    && permissionRoleDto.getAnonymous() == Permission.ANONYMOUS_DISABLE) {
                // 本项目权限配置中的*全部指向id，对于误匹配page等情况，需要单独处理
                if (antPathMatcher.extractPathWithinPattern(permissionRoleDto.getUrl(), url).matches("[\\D]+")) {
                    continue;
                }

                // 接口匹配成功，且不允许匿名访问，获取对应的授权角色列表
                List<Role> roleList = permissionRoleDto.getRoleList();
                // 接口不开放任何角色，或已被禁用，则返回拒绝标志
                if (CollUtil.isEmpty(roleList) || permissionRoleDto.getDeleted() == BaseEntity.ENTITY_DEACTIVATED) {
                    return SecurityConfig.createList("disabled");
                }
                // 获取角色列表的代码，并传递给后续流程
                List<String> roleCodeList = new ArrayList<>();
                for (Role role : roleList) {
                    roleCodeList.add(role.getCode());
                }
                return SecurityConfig.createList(roleCodeList.toArray(new String[]{}));
            }
        }
        // 如当前接口无权限配置，或支持匿名访问，则返回null
        // 此时，流程将不经过访问决策管理器，直接放行
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
