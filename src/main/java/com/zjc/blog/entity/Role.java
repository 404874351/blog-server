package com.zjc.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    public static final int ADMIN_ID = 1;
    public static final int TEST_ID = 2;
    public static final int USER_ID = 3;

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
}
