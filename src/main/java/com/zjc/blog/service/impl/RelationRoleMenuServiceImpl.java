package com.zjc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.entity.relation.RelationRoleMenu;
import com.zjc.blog.entity.relation.RelationRolePermission;
import com.zjc.blog.mapper.RelationRoleMenuMapper;
import com.zjc.blog.mapper.RelationRolePermissionMapper;
import com.zjc.blog.service.RelationRoleMenuService;
import com.zjc.blog.service.RelationRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationRoleMenuServiceImpl extends ServiceImpl<RelationRoleMenuMapper, RelationRoleMenu> implements RelationRoleMenuService {

    @Autowired
    private RelationRoleMenuMapper relationRoleMenuMapper;


    @Override
    public boolean removeByRoleId(Integer roleId) {
        QueryWrapper<RelationRoleMenu> removeQueryWrapper = new QueryWrapper<>();
        removeQueryWrapper.eq("role_id", roleId);
        this.remove(removeQueryWrapper);
        // 无论删除几条数据，只要不报错即可
        return true;
    }
}
