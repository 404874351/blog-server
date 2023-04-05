package com.zjc.blog.dto;

import lombok.Data;
import java.util.List;

/**
 * 用户菜单树，用于后台动态菜单配置
 */
@Data
public class UserMenuTreeDto {
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
     * 是否隐藏
     */
    private Integer hidden;
    /**
     * 子菜单列表
     */
    private List<UserMenuTreeDto> children;
}
