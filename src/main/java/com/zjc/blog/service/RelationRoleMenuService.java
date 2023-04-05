package com.zjc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjc.blog.entity.relation.RelationRoleMenu;
import com.zjc.blog.entity.relation.RelationRolePermission;

public interface RelationRoleMenuService extends IService<RelationRoleMenu> {

    boolean removeByRoleId(Integer roleId);

}
