package com.zjc.blog.security.handler;

import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.service.JwtService;
import com.zjc.blog.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.zjc.blog.security.filter.JwtAuthenticationFilter.TOKEN_STATE_NAME;

/**
 * spring security 登出成功的后续处理
 */
@Component
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Autowired
    private JwtService jwtService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResponseResult res;
        // 此时，spring security应该已经清空SecurityContext，以防万一，如认证没有清空，则手动清空
        // 事实上，由于将jwt过滤器放在logout之前，只要用户通过认证，authentication一定非空
        // 如果用户未通过认证，但依然请求登出，则token必然为空或出错
        if(authentication != null) {
            // 清空redis用户缓存，防止token误用
            String username = authentication.getName();
            jwtService.delTokenAndAuthority(username);
            // 返回成功响应
            res = ResponseResult.success(null);
            ResponseResult.outputSuccessResult(response, res);

            // 再次手动清空security应该已经清空SecurityContext
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        } else {
            // 获取token异常状态
            StateCodeMsg tokenState = (StateCodeMsg) request.getAttribute(TOKEN_STATE_NAME);
            if(tokenState != null) {
                res = ResponseResult.fail(tokenState, null);
            } else {
                res = ResponseResult.fail(StateCodeMsg.AUTHENTICATION_FAILED, null);
            }
            ResponseResult.outputSuccessResult(response, res);
        }

    }
}
