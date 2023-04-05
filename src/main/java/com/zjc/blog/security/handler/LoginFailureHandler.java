package com.zjc.blog.security.handler;

import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.exception.LoginException;
import com.zjc.blog.vo.ResponseResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * spring security 登录失败的后续处理
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        ResponseResult res;
        if (exception instanceof LoginException) {
            LoginException loginException = (LoginException) exception;
            res = ResponseResult.fail(loginException.getState(), null);
        } else {
            res = ResponseResult.fail(StateCodeMsg.LOGIN_FAIL, null);
        }
        ResponseResult.outputSuccessResult(response, res);
    }
}
