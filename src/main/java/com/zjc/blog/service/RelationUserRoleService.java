package com.zjc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjc.blog.entity.relation.RelationRolePermission;
import com.zjc.blog.entity.relation.RelationUserRole;

public interface RelationUserRoleService extends IService<RelationUserRole> {

    boolean removeByUserId(Integer userId);
}
