package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 用户更新 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateVo {
    /**
     * id，只允许用户修改自己的密码，因此登录后自动获取
     */
    private Integer id;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatarUrl;

}
