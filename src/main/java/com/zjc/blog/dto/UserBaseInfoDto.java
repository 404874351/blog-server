package com.zjc.blog.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户基本信息，常用于展示
 */
@Data
public class UserBaseInfoDto {
    /**
     * 角色id
     */
    private Integer id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatarUrl;
}
