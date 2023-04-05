package com.zjc.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjc.blog.dto.RoleAdminDto;
import com.zjc.blog.dto.RoleDashboardDto;
import com.zjc.blog.dto.RoleOptionDto;
import com.zjc.blog.entity.Role;
import com.zjc.blog.vo.*;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<Role> listByUserId(Integer userId);

    List<RoleOptionDto> roleOptionDtoList();

    List<RoleDashboardDto> roleDashboardDtoList();

    Page<RoleAdminDto> roleAdminDtoPage(PageVo pageVo, RoleVo roleVo);

    boolean save(RoleSaveVo roleSaveVo);

    boolean updateById(RoleUpdateVo roleUpdateVo);

    boolean updateRoleMenu(RoleMenuVo roleMenuVo);

    boolean updateRolePermission(RolePermissionVo rolePermissionVo);

    boolean updateDeleted(EntityDeletedVo entityDeletedVo);

}
