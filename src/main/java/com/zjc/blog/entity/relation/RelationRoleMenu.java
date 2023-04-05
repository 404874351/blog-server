package com.zjc.blog.entity.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关联 角色-菜单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationRoleMenu {
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 菜单id
     */
    private Integer menuId;
}
