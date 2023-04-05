package com.zjc.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseEntity {
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_GROUP = 1;

    public static final int LEVEL_TOP = 0;

    public static final int HIDDEN_DISABLE = 0;
    public static final int HIDDEN_ENABLE = 1;

    public static final String COMPONENT_LAYOUT = "Layout";
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
     * 菜单类型，0具体菜单，1菜单组，默认0
     */
    private Integer type;
    /**
     * 菜单层级，0顶层，正数代表具体层级，默认0
     */
    private Integer level;
    /**
     * 父级id，null没有父级，即处于顶层
     */
    private Integer parentId;
    /**
     * 是否隐藏，0否，1是，默认0
     */
    private Integer hidden;
}
