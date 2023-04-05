package com.zjc.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.dto.PermissionRoleDto;
import com.zjc.blog.dto.PermissionTreeDto;
import com.zjc.blog.dto.PermissionTreeOptionDto;
import com.zjc.blog.entity.Permission;
import com.zjc.blog.mapper.PermissionMapper;
import com.zjc.blog.service.PermissionService;
import com.zjc.blog.utils.MyBatisUtils;
import com.zjc.blog.vo.EntityDeletedVo;
import com.zjc.blog.vo.PermissionSaveVo;
import com.zjc.blog.vo.PermissionUpdateVo;
import com.zjc.blog.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public List<PermissionRoleDto> permissionRoleDtoList() {
        return permissionMapper.permissionRoleDtoList();
    }

    /**
     * 获取资源树形结构列表，支持查找
     * @param permissionVo
     * @return
     */
    @Override
    public List<PermissionTreeDto> permissionTreeDtoList(PermissionVo permissionVo) {
        // 获取原始权限列表，支持name和url查找
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StrUtil.isEmpty(permissionVo.getName()), "name", permissionVo.getName());
        queryWrapper.like(!StrUtil.isEmpty(permissionVo.getUrl()), "url", permissionVo.getUrl());
        List<Permission> permissionList = this.list(queryWrapper);
        // 获取顶层权限列表
        List<Permission> levelTopList = this.getLevelTopList(permissionList);
        // 根据父级id，汇总子权限列表
        Map<Integer, List<Permission>> childrenMap = this.aggregateChildren(permissionList);
        // 将所有权限列表生成为树形结构
        List<PermissionTreeDto> permissionTreeDtoList = levelTopList.stream().map(item -> {
            // 初始化树形节点，并进行属性赋值
            PermissionTreeDto permissionTreeDto = new PermissionTreeDto();
            BeanUtil.copyProperties(item, permissionTreeDto);
            // 添加对应的子节点列表
            List<PermissionTreeDto> children = BeanUtil.copyToList(childrenMap.get(item.getId()), PermissionTreeDto.class);
            permissionTreeDto.setChildren(children);
            // 添加完成后，在映射表中删除该项
            childrenMap.remove(item.getId());
            return permissionTreeDto;
        }).collect(Collectors.toList());
        // 如还存在子节点未处理，则统一追加
        if (!childrenMap.isEmpty()) {
            List<Permission> restChildren = new ArrayList<>();
            childrenMap.values().forEach(item -> {
                restChildren.addAll(item);
            });
            List<PermissionTreeDto> restChildrenDto = BeanUtil.copyToList(restChildren, PermissionTreeDto.class);
            permissionTreeDtoList.addAll(restChildrenDto);
        }

        return permissionTreeDtoList;
    }

    /**
     * 获取资源树形选项列表
     * @return
     */
    @Override
    public List<PermissionTreeOptionDto> permissionTreeOptionDtoList() {
        // 获取原始权限列表，由于所需内容较少，因此只查询部分字段
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name", "parent_id");
        List<Permission> permissionList = this.list(queryWrapper);
        // 获取顶层权限列表
        List<Permission> levelTopList = this.getLevelTopList(permissionList);
        // 根据父级id，汇总子权限列表
        Map<Integer, List<Permission>> childrenMap = this.aggregateChildren(permissionList);
        // 将所有权限列表生成为树形结构
        List<PermissionTreeOptionDto> permissionTreeOptionDtoList = levelTopList.stream().map(item -> {
            // 初始化树形节点，并进行属性赋值
            PermissionTreeOptionDto permissionTreeOptionDto = new PermissionTreeOptionDto();
            BeanUtil.copyProperties(item, permissionTreeOptionDto);
            // 添加对应的子节点列表
            List<PermissionTreeOptionDto> children = BeanUtil.copyToList(childrenMap.get(item.getId()), PermissionTreeOptionDto.class);
            permissionTreeOptionDto.setChildren(children);
            // 添加完成后，在映射表中删除该项
            childrenMap.remove(item.getId());
            return permissionTreeOptionDto;
        }).collect(Collectors.toList());
        // 由于未设置查询条件，且数据库外键约束，不存在子节点对应不到父节点的情况
        return permissionTreeOptionDtoList;
    }

    /**
     * 添加权限项
     * @param permissionSaveVo
     * @return
     */
    @Override
    public boolean saveItem(PermissionSaveVo permissionSaveVo) {
        Permission permission = new Permission();
        BeanUtil.copyProperties(permissionSaveVo, permission);
        permission.setType(Permission.TYPE_ITEM);
        return this.save(permission);
    }

    /**
     * 添加权限组
     * @param permissionSaveVo
     * @return
     */
    @Override
    public boolean saveGroup(PermissionSaveVo permissionSaveVo) {
        Permission permission = new Permission();
        BeanUtil.copyProperties(permissionSaveVo, permission);
        permission.setUrl(null);
        permission.setType(Permission.TYPE_GROUP);
        permission.setLevel(Permission.LEVEL_TOP);
        permission.setParentId(null);
        return this.save(permission);
    }

    /**
     * 更新权限项，允许更新多个字段
     * @param permissionUpdateVo
     * @return
     */
    @Override
    public boolean updateItem(PermissionUpdateVo permissionUpdateVo) {
        Permission permission = new Permission();
        permission.setUrl(permissionUpdateVo.getUrl());
        permission.setName(permissionUpdateVo.getName());
        permission.setAnonymous(permissionUpdateVo.getAnonymous());
        UpdateWrapper<Permission> updateWrapper = MyBatisUtils.createDefaultUpdateWrapper(Permission.class);
        updateWrapper.eq("id", permissionUpdateVo.getId());
        updateWrapper.eq("type", Permission.TYPE_ITEM);
        return this.update(permission, updateWrapper);
    }

    /**
     * 更新权限组，只允许更新名称
     * @param permissionUpdateVo
     * @return
     */
    @Override
    public boolean updateGroup(PermissionUpdateVo permissionUpdateVo) {
        Permission permission = new Permission();
        permission.setName(permissionUpdateVo.getName());
        UpdateWrapper<Permission> updateWrapper = MyBatisUtils.createDefaultUpdateWrapper(Permission.class);
        updateWrapper.eq("id", permissionUpdateVo.getId());
        updateWrapper.eq("type", Permission.TYPE_GROUP);
        return this.update(permission, updateWrapper);
    }

    /**
     * 更新权限禁用状态
     * 操作完成后，必须重新加载安全配置
     * @param entityDeletedVo
     * @return
     */
    @Override
    public boolean updateDeleted(EntityDeletedVo entityDeletedVo) {
        Permission permission = new Permission();
        BeanUtil.copyProperties(entityDeletedVo, permission);
        return this.updateById(permission);
    }

    /**
     * 删除权限项
     * 操作完成后，必须重新加载安全配置
     * @param id
     * @return
     */
    @Override
    public boolean removeItem(Integer id) {
        QueryWrapper<Permission> removeQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(Permission.class);
        removeQueryWrapper.eq("id", id);
        removeQueryWrapper.eq("type", Permission.TYPE_ITEM);
        return this.remove(removeQueryWrapper);
    }

    /**
     * 删除权限组，仅限于删除空白组
     * @param id
     * @return
     */
    @Override
    public boolean removeGroup(Integer id) {
        QueryWrapper<Permission> removeQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(Permission.class);
        removeQueryWrapper.eq("id", id);
        removeQueryWrapper.eq("type", Permission.TYPE_GROUP);
        return this.remove(removeQueryWrapper);
    }

    /**
     * 获取给定权限列表中的顶层部分
     * @param permissionList
     * @return
     */
    private List<Permission> getLevelTopList(List<Permission> permissionList) {
        return permissionList
                .stream()
                .filter(item -> item.getParentId() == null)
                .collect(Collectors.toList());
    }

    /**
     * 汇总子权限列表，以父级id为索引
     * @param permissionList
     * @return
     */
    private Map<Integer, List<Permission>> aggregateChildren(List<Permission> permissionList) {
        return permissionList
                .stream()
                .filter(item -> item.getParentId() != null)
                .collect(Collectors.groupingBy(item -> item.getParentId()));
    }
}
