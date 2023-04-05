package com.zjc.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限，对应服务接口
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity{
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_GROUP = 1;

    public static final int LEVEL_TOP = 0;

    public static final int ANONYMOUS_DISABLE = 0;
    public static final int ANONYMOUS_ENABLE = 1;

    /**
     * 权限路径，权限组通常为空
     */
    private String url;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限类型，0具体权限，1权限组，默认0
     */
    private Integer type;
    /**
     * 权限层级，0顶层，正数代表具体层级，默认0
     */
    private Integer level;
    /**
     * 父级id，null没有父级，即处于顶层
     */
    private Integer parentId;
    /**
     * 是否支持匿名访问，0否，1是，默认0
     */
    private Integer anonymous;
}
