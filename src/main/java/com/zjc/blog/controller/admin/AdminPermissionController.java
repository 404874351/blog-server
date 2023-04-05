package com.zjc.blog.controller.admin;

import com.zjc.blog.dto.PermissionTreeDto;
import com.zjc.blog.dto.PermissionTreeOptionDto;
import com.zjc.blog.entity.Permission;
import com.zjc.blog.security.filter.PermissionAuthorizationFilter;
import com.zjc.blog.service.PermissionService;
import com.zjc.blog.vo.EntityDeletedVo;
import com.zjc.blog.vo.PermissionSaveVo;
import com.zjc.blog.vo.PermissionUpdateVo;
import com.zjc.blog.vo.PermissionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/admin/permission")
public class AdminPermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionAuthorizationFilter permissionAuthorizationFilter;

    @GetMapping("/list")
    public List<PermissionTreeDto> list(@Validated PermissionVo permissionVo) {
        return permissionService.permissionTreeDtoList(permissionVo);
    }
    @GetMapping("/option")
    public List<PermissionTreeOptionDto> option() {
        return permissionService.permissionTreeOptionDtoList();
    }

    @PostMapping("/save")
    public boolean save(@Validated PermissionSaveVo permissionSaveVo) {
        boolean saveRes;
        if (permissionSaveVo.getType() == Permission.TYPE_GROUP) {
            saveRes = permissionService.saveGroup(permissionSaveVo);
        } else {
            saveRes = permissionService.saveItem(permissionSaveVo);
        }
        // 重新加载安全配置
        permissionAuthorizationFilter.clearDataSource();
        return saveRes;
    }

    @PostMapping("/remove/{id}")
    public boolean remove(@PathVariable("id") Integer id) {
        boolean removeRes = permissionService.removeById(id);
        // 重新加载安全配置
        permissionAuthorizationFilter.clearDataSource();
        return removeRes;
    }

    @PostMapping("/update/{id}")
    public boolean update(@Validated PermissionUpdateVo permissionUpdateVo, @PathVariable("id") Integer id) {
        permissionUpdateVo.setId(id);
        boolean updateRes;
        if (permissionUpdateVo.getType() == Permission.TYPE_GROUP) {
            updateRes = permissionService.updateGroup(permissionUpdateVo);
        } else {
            updateRes = permissionService.updateItem(permissionUpdateVo);
        }
        // 重新加载安全配置
        permissionAuthorizationFilter.clearDataSource();
        return updateRes;
    }

    @PostMapping("/update/{id}/deleted")
    public boolean updateDeleted(@Validated EntityDeletedVo entityDeletedVo, @PathVariable("id") Integer id) {
        entityDeletedVo.setId(id);
        boolean updateRes = permissionService.updateDeleted(entityDeletedVo);
        // 重新加载安全配置
        permissionAuthorizationFilter.clearDataSource();
        return updateRes;
    }


}
