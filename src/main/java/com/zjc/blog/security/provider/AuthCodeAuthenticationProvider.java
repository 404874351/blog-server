package com.zjc.blog.security.provider;

import cn.hutool.core.util.StrUtil;
import com.zjc.blog.entity.User;
import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.exception.LoginException;
import com.zjc.blog.security.details.AuthCodeUserDetails;
import com.zjc.blog.security.details.NormalUserDetails;
import com.zjc.blog.security.service.AuthCodeUserDetailsServiceImpl;
import com.zjc.blog.security.service.NormalUserDetailsServiceImpl;
import com.zjc.blog.security.token.AuthCodeAuthenticationToken;
import com.zjc.blog.security.token.NormalAuthenticationToken;
import com.zjc.blog.service.AuthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class AuthCodeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthCodeUserDetailsServiceImpl authCodeUserDetailsService;

    @Autowired
    private AuthCodeService authCodeService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthCodeAuthenticationToken authenticationToken = (AuthCodeAuthenticationToken) authentication;
        // 从未验证token中获取账号验证码
        String username = authenticationToken.getName();
        String code = authenticationToken.getCredentials().toString();
        // 检查用户名是否为空
        if (StrUtil.isEmpty(username)) {
            throw new LoginException(StateCodeMsg.USERNAME_NULL);
        }
        // 调用自己编写的UserDetailsService，返回userDetails
        AuthCodeUserDetails userDetails = (AuthCodeUserDetails) authCodeUserDetailsService.loadUserByUsername(username);
        // 检查验证码
        if (userDetails == null || !code.equals(userDetails.getCode())) {
            throw new LoginException(StateCodeMsg.AUTH_CODE_ERROR);
        }
        // 检查用户是否停用
        User user = userDetails.getUser();
        if (user.isEntityDeactivated()) {
            throw new LoginException(StateCodeMsg.USER_DEACTIVATED);
        }
        // 认证通过，清空验证码缓存
        authCodeService.delAuthCode(username);
        // 发放验证后的token
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
        return AuthCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
