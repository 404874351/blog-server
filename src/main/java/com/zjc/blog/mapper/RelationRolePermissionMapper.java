package com.zjc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjc.blog.entity.Permission;
import com.zjc.blog.entity.relation.RelationRolePermission;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationRolePermissionMapper extends BaseMapper<RelationRolePermission> {

}
