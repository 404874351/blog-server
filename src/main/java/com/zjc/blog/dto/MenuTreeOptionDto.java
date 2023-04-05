package com.zjc.blog.dto;

import lombok.Data;

import java.util.List;

/**
 * 菜单树选项，用于快速增减
 */
@Data
public class MenuTreeOptionDto {
    /**
     * 菜单id
     */
    private Integer id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 子菜单列表
     */
    private List<MenuTreeOptionDto> children;
}
