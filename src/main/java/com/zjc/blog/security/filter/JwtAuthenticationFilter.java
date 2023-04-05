package com.zjc.blog.security.filter;

import cn.hutool.core.util.StrUtil;
import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.exception.TokenException;
import com.zjc.blog.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * jwt验证授权过滤器，在logout过滤器之前执行
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public static final String TOKEN_STATE_NAME = "tokenState";

    public static final String REQUEST_USER_ID_NAME = "requestUserId";

    @Autowired
    private JwtService jwtService;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取jwt
        String jwt = jwtService.getJwtByAuthorization(request.getHeader("Authorization"));
        // 匿名访问，交由后续处理
        // 白名单路径可自由通过，其他都拦截
        if(StrUtil.isEmpty(jwt)) {
            setTokenStateInRequest(StateCodeMsg.TOKEN_NOT_EXIST, request);
            chain.doFilter(request, response);
            return;
        }
        // 实名访问，检查token是否非法或过期
        Claims claims = jwtService.parseToken(jwt);
        if(claims == null) {
            throwTokenException(StateCodeMsg.TOKEN_ILLEGAL, request);
        }
        // 此时，token存在且可以被解析，需要结合缓存判定用户身份
        // 从redis获取用户token和authorities
        String username = claims.getSubject();
        String token = jwtService.getToken(username);
        List<GrantedAuthority> authorityList = jwtService.getAuthority(username);
        // 判定缓存的token是否为空或不一致
        if(token == null) {
            throwTokenException(StateCodeMsg.TOKEN_INVALID, request);
        }
        if(!token.equals(jwt)) {
            throwTokenException(StateCodeMsg.TOKEN_ILLEGAL, request);
        }

        // 获取用户id，保存到请求域，供后续使用
        Integer id = claims.get("id", Integer.class);
        request.setAttribute(REQUEST_USER_ID_NAME, id);

        // 为本次请求创建鉴权token，供接口授权判定，适用于接口权限写死的情况
        // 在logout时，需要通过鉴权，获取username，从而进行redis删除操作
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorityList);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);
    }

    public void setTokenStateInRequest(StateCodeMsg state, HttpServletRequest request) {
        request.setAttribute(TOKEN_STATE_NAME, state);
    }

    public void throwTokenException(StateCodeMsg state, HttpServletRequest request) {
        setTokenStateInRequest(state, request);
        throw new TokenException(state);
    }



}
