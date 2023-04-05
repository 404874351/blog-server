package com.zjc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjc.blog.entity.Permission;
import com.zjc.blog.entity.relation.RelationRolePermission;

public interface RelationRolePermissionService extends IService<RelationRolePermission> {

    boolean removeByRoleId(Integer roleId);

}
