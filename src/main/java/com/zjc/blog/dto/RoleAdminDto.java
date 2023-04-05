package com.zjc.blog.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 后台角色
 */
@Data
public class RoleAdminDto {
    /**
     * 角色id
     */
    private Integer id;
    /**
     * 角色代码
     */
    private String code;
    /**
     * 角色名
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否禁用
     */
    private Integer deleted;
    /**
     * 权限id列表
     */
    private List<Integer> permissionIdList;

    /**
     * 菜单id列表
     */
    private List<Integer> menuIdList;
}
