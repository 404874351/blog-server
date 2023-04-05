package com.zjc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.entity.relation.RelationRolePermission;
import com.zjc.blog.entity.relation.RelationUserRole;
import com.zjc.blog.mapper.RelationRolePermissionMapper;
import com.zjc.blog.mapper.RelationUserRoleMapper;
import com.zjc.blog.service.RelationRolePermissionService;
import com.zjc.blog.service.RelationUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationUserRoleServiceImpl extends ServiceImpl<RelationUserRoleMapper, RelationUserRole> implements RelationUserRoleService {

    @Autowired
    private RelationUserRoleMapper relationUserRoleMapper;


    @Override
    public boolean removeByUserId(Integer userId) {
        QueryWrapper<RelationUserRole> removeQueryWrapper = new QueryWrapper<>();
        removeQueryWrapper.eq("user_id", userId);
        return this.remove(removeQueryWrapper);
    }
}
