package com.zjc.blog.entity.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关联 用户-角色
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationUserRole {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 角色id
     */
    private Integer roleId;
}
