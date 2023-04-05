package com.zjc.blog.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 权限树，用于列表展示
 */
@Data
public class PermissionTreeDto {
    /**
     * 权限id
     */
    private Integer id;
    /**
     * 权限路径
     */
    private String url;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限类型
     */
    private Integer type;
    /**
     * 是否支持匿名访问
     */
    private Integer anonymous;
    /**
     * 是否禁用
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 子权限列表
     */
    private List<PermissionTreeDto> children;
}
