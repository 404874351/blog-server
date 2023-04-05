package com.zjc.blog.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色选项，用于快速增减
 */
@Data
public class RoleOptionDto {
    /**
     * 角色id
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
}
