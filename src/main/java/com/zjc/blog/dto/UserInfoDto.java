package com.zjc.blog.dto;

import lombok.Data;
import java.util.List;

/**
 * 用户信息
 */
@Data
public class UserInfoDto {
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
    /**
     * 用户对应的角色列表
     */
    private List<RoleOptionDto> roleList;
}
