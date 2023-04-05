package com.zjc.blog.exception;

import com.zjc.blog.enums.StateCodeMsg;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * 用户登录异常
 */
@Getter
@Setter
public class LoginException extends InternalAuthenticationServiceException {
    private StateCodeMsg state;

    public LoginException() {
        super(StateCodeMsg.USERNAME_NULL.getMsg());
        this.state = StateCodeMsg.USERNAME_NULL;
    }

    public LoginException(StateCodeMsg state) {
        super(state.getMsg());
        this.state = state;
    }
}
