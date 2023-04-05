package com.zjc.blog.dto;

import lombok.Data;

/**
 * 仪表盘角色信息
 */
@Data
public class RoleDashboardDto {
    /**
     * 角色id
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 计数
     */
    private long count;
}
