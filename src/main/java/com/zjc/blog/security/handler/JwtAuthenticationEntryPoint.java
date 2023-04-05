package com.zjc.blog.security.handler;

import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.service.JwtService;
import com.zjc.blog.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.zjc.blog.security.filter.JwtAuthenticationFilter.TOKEN_STATE_NAME;

/**
 * 处理匿名用户访问无权限异常，即jwt认证不通过，过滤器报错后到达此处
 * 如未登录，登录态过期，登录态非法
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 获取token异常状态
        StateCodeMsg tokenState = (StateCodeMsg) request.getAttribute(TOKEN_STATE_NAME);
        // 保存返回结果
        ResponseResult res;
        if(tokenState != null) {
            res = ResponseResult.fail(tokenState, null);
        } else {
            res = ResponseResult.fail(StateCodeMsg.AUTHENTICATION_FAILED, null);
        }
        ResponseResult.outputSuccessResult(response, res);
    }
}
