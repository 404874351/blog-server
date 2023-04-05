package com.zjc.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.RoleAdminDto;
import com.zjc.blog.dto.RoleOptionDto;
import com.zjc.blog.security.filter.PermissionAuthorizationFilter;
import com.zjc.blog.service.RoleService;
import com.zjc.blog.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/admin/role")
public class AdminRoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionAuthorizationFilter permissionAuthorizationFilter;

    @GetMapping("/page")
    public Page<RoleAdminDto> page(@Validated PageVo pageVo, @Validated RoleVo roleVo) {
        return roleService.roleAdminDtoPage(pageVo, roleVo);
    }

    @GetMapping("/option")
    public List<RoleOptionDto> option() {
        return roleService.roleOptionDtoList();
    }

    @PostMapping("/save")
    public boolean save(@Validated RoleSaveVo roleSaveVo) {
        return roleService.save(roleSaveVo);
    }

    @PostMapping("/remove/{id}")
    public boolean remove(@PathVariable("id") Integer id) {
        return roleService.removeById(id);
    }

    @PostMapping("/update/{id}")
    public boolean update(@Validated RoleUpdateVo roleUpdateVo, @PathVariable("id") Integer id) {
        roleUpdateVo.setId(id);
        return roleService.updateById(roleUpdateVo);
    }

    @PostMapping("/update/{id}/deleted")
    public boolean updateDeleted(@Validated EntityDeletedVo entityDeletedVo, @PathVariable("id") Integer id) {
        entityDeletedVo.setId(id);
        return roleService.updateDeleted(entityDeletedVo);
    }

    @PostMapping("/update/{id}/menu")
    public boolean updateRoleMenu(@Validated RoleMenuVo roleMenuVo, @PathVariable("id") Integer id) {
        roleMenuVo.setId(id);
        return roleService.updateRoleMenu(roleMenuVo);
    }

    @PostMapping("/update/{id}/permission")
    public boolean updateRolePermission(@Validated RolePermissionVo rolePermissionVo, @PathVariable("id") Integer id) {
        rolePermissionVo.setId(id);
        boolean updateRes = roleService.updateRolePermission(rolePermissionVo);
        // 重新加载安全配置
        permissionAuthorizationFilter.clearDataSource();
        return updateRes;
    }

}
