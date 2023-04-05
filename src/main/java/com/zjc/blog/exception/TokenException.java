package com.zjc.blog.exception;

import com.zjc.blog.enums.StateCodeMsg;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

/**
 * token异常
 */
@Getter
@Setter
public class TokenException extends AuthenticationException {
    private StateCodeMsg state;

    public TokenException(StateCodeMsg state) {
        super(state.getMsg());
        this.state = state;
    }
}
