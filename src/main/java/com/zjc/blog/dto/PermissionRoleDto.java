package com.zjc.blog.dto;

import com.zjc.blog.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * 权限与关联角色列表，用于授权
 */
@Data
public class PermissionRoleDto {
    /**
     * 权限id
     */
    private Integer id;
    /**
     * 权限路径
     */
    private String url;
    /**
     * 是否支持匿名访问
     */
    private Integer anonymous;
    /**
     * 是否禁用
     */
    private Integer deleted;
    /**
     * 角色列表
     */
    private List<Role> roleList;
}
