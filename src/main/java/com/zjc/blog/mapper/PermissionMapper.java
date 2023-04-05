package com.zjc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjc.blog.dto.PermissionRoleDto;
import com.zjc.blog.entity.Permission;
import com.zjc.blog.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    List<PermissionRoleDto> permissionRoleDtoList();
}
