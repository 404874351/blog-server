package com.zjc.blog.handler;

import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.exception.ServiceException;
import com.zjc.blog.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * controller全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler({RuntimeException.class})
    public ResponseResult onRuntimeException(RuntimeException exception) {
        log.error(exception.getMessage());
        return ResponseResult.fail(StateCodeMsg.ACCESS_FAILED, null);
    }

    /**
     * 接口数据校验异常
     * @param exception
     * @return
     */
    @ExceptionHandler({BindException.class})
    public ResponseResult onBindException(BindException exception) {
        log.error(exception.getMessage());
        List<String> messageList = exception.getAllErrors().stream().map(item -> item.getDefaultMessage()).collect(Collectors.toList());
        return ResponseResult.fail(StateCodeMsg.PARAMETER_ILLEGAL, messageList);
    }

    /**
     * 数据插入唯一性异常
     * @param exception
     * @return
     */
    @ExceptionHandler({DuplicateKeyException.class})
    public ResponseResult onDuplicateKeyException(DuplicateKeyException exception) {
        log.error(exception.getCause().toString());
        return ResponseResult.fail(StateCodeMsg.SQL_UNIQUE_ERROR, exception.getCause().toString());
    }

    /**
     * 数据插入完整性异常
     * @param exception
     * @return
     */
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseResult onDataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.error(exception.getCause().toString());
        return ResponseResult.fail(StateCodeMsg.SQL_INTEGRITY_ERROR, exception.getCause().toString());
    }

    /**
     * 具体业务异常
     * @param exception
     * @return
     */
    @ExceptionHandler({ServiceException.class})
    public ResponseResult onUserException(ServiceException exception) {
        log.error(exception.getState().getMsg());
        return ResponseResult.fail(exception.getState(), null);
    }

    @ExceptionHandler({SQLException.class})
    public ResponseResult onSqlException(SQLException exception) {
        log.error(exception.getMessage());
        return ResponseResult.fail(StateCodeMsg.SQL_FAILED, null);
    }
}
