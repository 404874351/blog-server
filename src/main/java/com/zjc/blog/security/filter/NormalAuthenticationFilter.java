package com.zjc.blog.security.filter;

import com.zjc.blog.security.token.NormalAuthenticationToken;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 普通登录过滤器，使用账号密码
 */
public class NormalAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * 配置拦截的路径和方法
     */
    private static final AntPathRequestMatcher REQUEST_MATCHER = new AntPathRequestMatcher(
            "/login", "POST"
    );

    private boolean postOnly = true;

    public NormalAuthenticationFilter() {
        super(REQUEST_MATCHER);
    }

    public NormalAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // 验证请求方法
        if (this.postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("只允许POST方式请求登录接口");
        }
        // 获取登录参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 封装未验证的token
        NormalAuthenticationToken authenticationToken = new NormalAuthenticationToken(username, password);
        // 通过provider验证，返回验证后的token
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}
