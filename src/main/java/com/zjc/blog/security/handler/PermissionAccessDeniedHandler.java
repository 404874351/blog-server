package com.zjc.blog.security.handler;

import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.vo.ResponseResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理已登录用户访问无权限异常
 */
@Component
public class PermissionAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult res = ResponseResult.fail(StateCodeMsg.ACCESS_DENIED, null);
        ResponseResult.outputSuccessResult(response, res);
    }
}
