package com.zjc.blog.security.provider;

import cn.hutool.core.util.StrUtil;
import com.zjc.blog.entity.User;
import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.exception.LoginException;
import com.zjc.blog.security.details.NormalUserDetails;
import com.zjc.blog.security.service.NormalUserDetailsServiceImpl;
import com.zjc.blog.security.token.NormalAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class NormalAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private NormalUserDetailsServiceImpl normalUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        NormalAuthenticationToken authenticationToken = (NormalAuthenticationToken) authentication;
        // 从未验证token中获取账号密码
        String username = authenticationToken.getName();
        String password = authenticationToken.getCredentials().toString();
        // 检查用户名是否为空
        if (StrUtil.isEmpty(username)) {
            throw new LoginException(StateCodeMsg.USERNAME_NULL);
        }
        // 调用自己编写的UserDetailsService，返回userDetails，包含加密密码和相关权限
        NormalUserDetails userDetails = (NormalUserDetails) normalUserDetailsService.loadUserByUsername(username);
        // 检查密码
        if (userDetails == null || !bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            throw new LoginException(StateCodeMsg.USERNAME_OR_PASSWORD_ERROR);
        }
        // 检查用户是否停用
        User user = userDetails.getUser();
        if (user.isEntityDeactivated()) {
            throw new LoginException(StateCodeMsg.USER_DEACTIVATED);
        }
        // 认证通过，发放验证后的token
        authenticationToken.setAuthenticated(true);
        // 添加details = user，方便登录成功处理
        authenticationToken.setDetails(user);
        return authenticationToken;
    }

    /**
     * 定义支持处理的token类型
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return NormalAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
