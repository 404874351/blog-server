package com.zjc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjc.blog.entity.relation.RelationRolePermission;
import com.zjc.blog.entity.relation.RelationUserRole;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationUserRoleMapper extends BaseMapper<RelationUserRole> {

}
