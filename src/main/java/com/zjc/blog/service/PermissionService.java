package com.zjc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjc.blog.dto.PermissionRoleDto;
import com.zjc.blog.dto.PermissionTreeDto;
import com.zjc.blog.dto.PermissionTreeOptionDto;
import com.zjc.blog.entity.Permission;
import com.zjc.blog.vo.EntityDeletedVo;
import com.zjc.blog.vo.PermissionSaveVo;
import com.zjc.blog.vo.PermissionUpdateVo;
import com.zjc.blog.vo.PermissionVo;

import java.util.List;

public interface PermissionService extends IService<Permission> {

    List<PermissionRoleDto> permissionRoleDtoList();

    List<PermissionTreeDto> permissionTreeDtoList(PermissionVo permissionVo);

    List<PermissionTreeOptionDto> permissionTreeOptionDtoList();

    boolean saveItem(PermissionSaveVo permissionSaveVo);

    boolean saveGroup(PermissionSaveVo permissionSaveVo);

    boolean updateItem(PermissionUpdateVo permissionUpdateVo);

    boolean updateGroup(PermissionUpdateVo permissionUpdateVo);

    boolean updateDeleted(EntityDeletedVo entityDeletedVo);

    boolean removeItem(Integer id);

    boolean removeGroup(Integer id);


}
