package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机号
     */
    private String phone;
}
