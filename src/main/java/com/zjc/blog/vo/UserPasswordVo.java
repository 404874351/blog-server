package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用户密码 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordVo {
    /**
     * id，只允许用户修改自己的密码，因此登录后自动获取
     */
    private Integer id;
    /**
     * 密码
     */
    @NotBlank(message = "原密码不能为空")
    private String password;
    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$_&*+-])[0-9a-zA-Z!@#$_&*+-]{8,18}$", message = "密码必须包含数字、字母和特殊字符，长度为8-18位")
    private String newPassword;
}
