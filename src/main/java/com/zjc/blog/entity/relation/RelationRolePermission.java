package com.zjc.blog.entity.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关联 角色-权限
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationRolePermission {
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 权限id
     */
    private Integer permissionId;
}
