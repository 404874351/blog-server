package com.zjc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.entity.Permission;
import com.zjc.blog.entity.relation.RelationRolePermission;
import com.zjc.blog.mapper.PermissionMapper;
import com.zjc.blog.mapper.RelationRolePermissionMapper;
import com.zjc.blog.service.PermissionService;
import com.zjc.blog.service.RelationRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationRolePermissionServiceImpl extends ServiceImpl<RelationRolePermissionMapper, RelationRolePermission> implements RelationRolePermissionService {

    @Autowired
    private RelationRolePermissionMapper relationRolePermissionMapper;


    @Override
    public boolean removeByRoleId(Integer roleId) {
        QueryWrapper<RelationRolePermission> removeQueryWrapper = new QueryWrapper<>();
        removeQueryWrapper.eq("role_id", roleId);
        this.remove(removeQueryWrapper);
        // 无论删除几条数据，只要不报错即可
        return true;
    }
}
