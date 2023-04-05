package com.zjc.blog.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 菜单树，用于列表展示
 */
@Data
public class MenuTreeDto {
    /**
     * 菜单id
     */
    private Integer id;
    /**
     * 菜单代码
     */
    private String code;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单路径
     */
    private String path;
    /**
     * 菜单组件
     */
    private String component;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单类型
     */
    private Integer type;
    /**
     * 父级id
     */
    private Integer parentId;
    /**
     * 是否隐藏
     */
    private Integer hidden;
    /**
     * 是否禁用
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 子菜单列表
     */
    private List<MenuTreeDto> children;
}
