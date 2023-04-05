package com.zjc.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态码及其提示信息
 */
@Getter
@AllArgsConstructor
public enum StateCodeMsg {
    /**
     * 1xxx 请求成功
     */
    SUCCESS(1000, "请求成功"),

    /**
     * 2xxx 登录控制
     */
    LOGIN_FAIL(2000, "登录流程异常，请联系管理员"),
    USERNAME_NULL(2001, "用户名为空"),
    USER_DEACTIVATED(2002, "该账号已停用，请联系管理员"),
    USERNAME_OR_PASSWORD_ERROR(2003, "用户名或密码不正确"),
    AUTH_CODE_ERROR(2004, "用户名或验证码不正确"),

    /**
     * 3xxx 用户认证异常
     */
    AUTHENTICATION_FAILED(3000, "用户认证流程异常，请联系管理员"),
    TOKEN_NOT_EXIST(3001, "Token不存在，请先登录"),
    TOKEN_ILLEGAL(3002, "Token非法或过期，请重新登录"),
    TOKEN_INVALID(3003, "Token已失效，请重新登录"),

    /**
     * 4xxx 业务访问异常
     */
    ACCESS_FAILED(4000, "处理流程异常，访问失败"),
    ACCESS_DENIED(4001, "权限不足，拒绝访问"),
    PARAMETER_ILLEGAL(4002, "请求参数非法，请检查数据合法性"),
    SQL_FAILED(4003, "数据库操作失败，请检查逻辑错误"),
    SQL_UNIQUE_ERROR(4004, "数据库字段唯一性错误"),
    SQL_INTEGRITY_ERROR(4005, "数据库字段完整性错误"),
    RAW_PASSWORD_ERROR(4006, "原密码不正确"),
    USER_NOT_EXIST(4007, "用户不存在"),
    PHONE_EXIST(4008, "该手机号已被他人注册"),
    COMMENT_TOP_EXIST(4009, "该文章的置顶评论已存在且唯一"),
    COMMENT_NOT_LEVEL_TOP(4010, "该评论不是顶级评论"),
    AUTH_CODE_SEND_TOO_FAST(4011, "验证码发送过于频繁"),
    AUTH_CODE_INVALID(4012, "验证码错误或已失效"),
    AUTH_CODE_SEND_ERROR(4012, "验证码发送失败，请稍后再试"),

    ;

    private int code;

    private String msg;

}
