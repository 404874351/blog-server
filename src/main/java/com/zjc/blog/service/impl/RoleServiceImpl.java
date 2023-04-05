package com.zjc.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.dto.RoleAdminDto;
import com.zjc.blog.dto.RoleDashboardDto;
import com.zjc.blog.dto.RoleOptionDto;
import com.zjc.blog.entity.Role;
import com.zjc.blog.entity.relation.RelationRoleMenu;
import com.zjc.blog.entity.relation.RelationRolePermission;
import com.zjc.blog.mapper.RoleMapper;
import com.zjc.blog.service.RelationRoleMenuService;
import com.zjc.blog.service.RelationRolePermissionService;
import com.zjc.blog.service.RoleService;
import com.zjc.blog.utils.MyBatisUtils;
import com.zjc.blog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RelationRoleMenuService relationRoleMenuService;

    @Autowired
    private RelationRolePermissionService relationRolePermissionService;

    /**
     * 获取用户对应的角色列表
     * @param userId
     * @return
     */
    @Override
    public List<Role> listByUserId(Integer userId) {
        return roleMapper.selectListByUserId(userId);
    }

    /**
     * 获取全部角色选项列表
     * @return
     */
    @Override
    public List<RoleOptionDto> roleOptionDtoList() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name");
        List<Role> roleList = this.list(queryWrapper);
        return BeanUtil.copyToList(roleList, RoleOptionDto.class);
    }

    @Override
    public List<RoleDashboardDto> roleDashboardDtoList() {
        return roleMapper.selectRoleDashboardDtoList();
    }

    /**
     * 分页获取后台角色
     * @param pageVo
     * @param roleVo
     * @return
     */
    @Override
    public Page<RoleAdminDto> roleAdminDtoPage(PageVo pageVo, RoleVo roleVo) {
        // 构造分页
        Page<RoleAdminDto> page = new Page<>();
        BeanUtil.copyProperties(pageVo, page);
        // 构造分页查询条件
        QueryWrapper<RoleAdminDto> pageQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(RoleAdminDto.class);
        pageQueryWrapper.like(!StrUtil.isEmpty(roleVo.getCode()), "code", roleVo.getCode());
        pageQueryWrapper.like(!StrUtil.isEmpty(roleVo.getName()), "name", roleVo.getName());
        pageQueryWrapper.like(!StrUtil.isEmpty(roleVo.getDescription()), "description", roleVo.getDescription());
        // 查询数据
        List<RoleAdminDto> records = roleMapper.selectRoleAdminDtoPage(
                (page.getCurrent() - 1) * page.getSize(),
                page.getSize(),
                pageQueryWrapper
        );
        page.setRecords(records);

        // 构造总数查询条件
        QueryWrapper<Role> countQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(Role.class);
        countQueryWrapper.like(!StrUtil.isEmpty(roleVo.getCode()), "code", roleVo.getCode());
        countQueryWrapper.like(!StrUtil.isEmpty(roleVo.getName()), "name", roleVo.getName());
        countQueryWrapper.like(!StrUtil.isEmpty(roleVo.getDescription()), "description", roleVo.getDescription());
        // 获取总数
        long total = this.count(countQueryWrapper);
        page.setTotal(total);
        // 获取总页数
        page.setPages((total / page.getSize()) + 1);

        return page;
    }

    /**
     * 添加角色
     * @param roleSaveVo
     * @return
     */
    @Override
    public boolean save(RoleSaveVo roleSaveVo) {
        Role role = new Role();
        BeanUtil.copyProperties(roleSaveVo, role);
        return this.save(role);
    }

    /**
     * 更新角色
     * @param roleUpdateVo
     * @return
     */
    @Override
    public boolean updateById(RoleUpdateVo roleUpdateVo) {
        Role role = new Role();
        BeanUtil.copyProperties(roleUpdateVo, role);
        return this.updateById(role);
    }

    /**
     * 更新角色分配菜单列表
     * @param roleMenuVo
     * @return
     */
    @Override
    public boolean updateRoleMenu(RoleMenuVo roleMenuVo) {
        // 删除原有菜单列表
        boolean removeRes = relationRoleMenuService.removeByRoleId(roleMenuVo.getId());
        if (CollUtil.isEmpty(roleMenuVo.getMenuIdList())) {
            return removeRes;
        }
        // 插入新菜单列表
        List<RelationRoleMenu> relationRoleMenuList = roleMenuVo.getMenuIdList().stream().map(item -> {
            RelationRoleMenu relationRoleMenu = new RelationRoleMenu();
            relationRoleMenu.setRoleId(roleMenuVo.getId());
            relationRoleMenu.setMenuId(item);
            return relationRoleMenu;
        }).collect(Collectors.toList());
        boolean saveRes = relationRoleMenuService.saveBatch(relationRoleMenuList);

        return removeRes && saveRes;
    }

    /**
     * 更新角色分配权限列表
     * @param rolePermissionVo
     * @return
     */
    @Override
    public boolean updateRolePermission(RolePermissionVo rolePermissionVo) {
        // 删除原有权限列表
        boolean removeRes = relationRolePermissionService.removeByRoleId(rolePermissionVo.getId());
        if (CollUtil.isEmpty(rolePermissionVo.getPermissionIdList())) {
            return removeRes;
        }
        // 插入新权限列表
        List<RelationRolePermission> relationRolePermissionList = rolePermissionVo.getPermissionIdList().stream().map(item -> {
            RelationRolePermission relationRolePermission = new RelationRolePermission();
            relationRolePermission.setRoleId(rolePermissionVo.getId());
            relationRolePermission.setPermissionId(item);
            return relationRolePermission;
        }).collect(Collectors.toList());
        boolean saveRes = relationRolePermissionService.saveBatch(relationRolePermissionList);

        return removeRes && saveRes;
    }

    /**
     * 更新禁用状态
     * @param entityDeletedVo
     * @return
     */
    @Override
    public boolean updateDeleted(EntityDeletedVo entityDeletedVo) {
        Role role = new Role();
        BeanUtil.copyProperties(entityDeletedVo, role);
        return this.updateById(role);
    }

}
