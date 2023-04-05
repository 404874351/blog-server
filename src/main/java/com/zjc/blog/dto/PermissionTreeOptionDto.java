package com.zjc.blog.dto;

import lombok.Data;
import java.util.List;

/**
 * 权限树选项，用于快速增减
 */
@Data
public class PermissionTreeOptionDto {
    /**
     * 权限id
     */
    private Integer id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 子权限列表
     */
    private List<PermissionTreeOptionDto> children;
}
