package com.zjc.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.dto.MenuTreeDto;
import com.zjc.blog.dto.MenuTreeOptionDto;
import com.zjc.blog.dto.UserMenuTreeDto;
import com.zjc.blog.entity.Menu;
import com.zjc.blog.entity.User;
import com.zjc.blog.mapper.MenuMapper;
import com.zjc.blog.mapper.UserMapper;
import com.zjc.blog.service.MenuService;
import com.zjc.blog.service.UserService;
import com.zjc.blog.utils.MyBatisUtils;
import com.zjc.blog.vo.EntityDeletedVo;
import com.zjc.blog.vo.MenuSaveVo;
import com.zjc.blog.vo.MenuUpdateVo;
import com.zjc.blog.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取菜单树列表，支持查找
     * @param menuVo
     * @return
     */
    @Override
    public List<MenuTreeDto> menuTreeDtoList(MenuVo menuVo) {
        // 获取原始菜单列表
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StrUtil.isEmpty(menuVo.getCode()), "code", menuVo.getCode());
        queryWrapper.like(!StrUtil.isEmpty(menuVo.getName()), "name", menuVo.getName());
        queryWrapper.like(!StrUtil.isEmpty(menuVo.getPath()), "path", menuVo.getPath());
        List<Menu> menuList = this.list(queryWrapper);
        // 获取顶层菜单列表
        List<Menu> levelTopList = this.getLevelTopList(menuList);
        // 根据父级id，汇总子菜单列表
        Map<Integer, List<Menu>> childrenMap = this.aggregateChildren(menuList);
        // 将所有菜单列表生成为树形结构
        List<MenuTreeDto> menuTreeDtoList = levelTopList.stream().map(item -> {
            // 初始化树形节点，并进行属性赋值
            MenuTreeDto menuTreeDto = new MenuTreeDto();
            BeanUtil.copyProperties(item, menuTreeDto);
            // 添加对应的子节点列表
            List<MenuTreeDto> children = BeanUtil.copyToList(childrenMap.get(item.getId()), MenuTreeDto.class);
            menuTreeDto.setChildren(children);
            // 添加完成后，在映射表中删除该项
            childrenMap.remove(item.getId());
            return menuTreeDto;
        }).collect(Collectors.toList());
        // 如还存在子节点未处理，则统一追加
        if (!childrenMap.isEmpty()) {
            List<Menu> restChildren = new ArrayList<>();
            childrenMap.values().forEach(item -> {
                restChildren.addAll(item);
            });
            List<MenuTreeDto> restChildrenDto = BeanUtil.copyToList(restChildren, MenuTreeDto.class);
            menuTreeDtoList.addAll(restChildrenDto);
        }

        return menuTreeDtoList;
    }

    /**
     * 获取菜单树选项列表
     * @return
     */
    @Override
    public List<MenuTreeOptionDto> menuTreeOptionDtoList() {
        // 获取原始菜单列表，由于所需内容较少，因此只查询部分字段
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name", "parent_id");
        List<Menu> menuList = this.list(queryWrapper);
        // 获取顶层菜单列表
        List<Menu> levelTopList = this.getLevelTopList(menuList);
        // 根据父级id，汇总子菜单列表
        Map<Integer, List<Menu>> childrenMap = this.aggregateChildren(menuList);
        // 将所有菜单列表生成为树形结构
        List<MenuTreeOptionDto> menuTreeOptionDtoList = levelTopList.stream().map(item -> {
            // 初始化树形节点，并进行属性赋值
            MenuTreeOptionDto menuTreeOptionDto = new MenuTreeOptionDto();
            BeanUtil.copyProperties(item, menuTreeOptionDto);
            // 添加对应的子节点列表
            List<MenuTreeOptionDto> children = BeanUtil.copyToList(childrenMap.get(item.getId()), MenuTreeOptionDto.class);
            menuTreeOptionDto.setChildren(children);
            // 添加完成后，在映射表中删除该项
            childrenMap.remove(item.getId());
            return menuTreeOptionDto;
        }).collect(Collectors.toList());
        // 由于未设置查询条件，且数据库外键约束，不存在子节点对应不到父节点的情况
        return menuTreeOptionDtoList;
    }

    /**
     * 获取用户菜单树列表，本质上通过用户对应的角色列表，查找与之绑定的菜单
     * @param userId
     * @return
     */
    @Override
    public List<UserMenuTreeDto> userMenuTreeDtoList(Integer userId) {
        // 查询用户所属角色对应的所有菜单列表，要求菜单未被禁用
        List<Menu> menuList = menuMapper.selectListByUserId(userId);
        // 获取顶层菜单列表
        List<Menu> levelTopList = this.getLevelTopList(menuList);
        // 根据父级id，汇总子菜单列表
        Map<Integer, List<Menu>> childrenMap = this.aggregateChildren(menuList);
        // 将所有菜单列表生成为树形结构
        List<UserMenuTreeDto> userMenuTreeDtoList = levelTopList.stream().map(item -> {
            // 初始化树形节点，并进行属性赋值
            UserMenuTreeDto userMenuTreeDto = new UserMenuTreeDto();
            BeanUtil.copyProperties(item, userMenuTreeDto);
            // 添加对应的子节点列表
            List<UserMenuTreeDto> children = BeanUtil.copyToList(childrenMap.get(item.getId()), UserMenuTreeDto.class);
            userMenuTreeDto.setChildren(children);
            // 添加完成后，在映射表中删除该项
            childrenMap.remove(item.getId());
            return userMenuTreeDto;
        }).collect(Collectors.toList());

        // 如父节点被禁用，而子节点未被禁用，则子节点无法获取
        // 但是，通常不建议如此设置，请优先禁用子节点
        return userMenuTreeDtoList;
    }

    /**
     * 添加菜单项
     * @param menuSaveVo
     * @return
     */
    @Override
    public boolean saveItem(MenuSaveVo menuSaveVo) {
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuSaveVo, menu);
        menu.setType(Menu.TYPE_ITEM);
        return this.save(menu);
    }

    /**
     * 添加菜单组
     * @param menuSaveVo
     * @return
     */
    @Override
    public boolean saveGroup(MenuSaveVo menuSaveVo) {
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuSaveVo, menu);
        menu.setType(Menu.TYPE_GROUP);
        menu.setLevel(Menu.LEVEL_TOP);
        menu.setParentId(null);
        return this.save(menu);
    }

    /**
     * 更新菜单，但无法更新类型字段
     * @param menuUpdateVo
     * @return
     */
    @Override
    public boolean updateById(MenuUpdateVo menuUpdateVo) {
        Menu menu = new Menu();
        BeanUtil.copyProperties(menuUpdateVo, menu);
        menu.setType(null);
        return this.updateById(menu);
    }

    /**
     * 更新菜单禁用状态
     * @param entityDeletedVo
     * @return
     */
    @Override
    public boolean updateDeleted(EntityDeletedVo entityDeletedVo) {
        Menu menu = new Menu();
        BeanUtil.copyProperties(entityDeletedVo, menu);
        return this.updateById(menu);
    }

    /**
     * 删除菜单项
     * @param id
     * @return
     */
    @Override
    public boolean removeItem(Integer id) {
        QueryWrapper<Menu> removeQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(Menu.class);
        removeQueryWrapper.eq("id", id);
        removeQueryWrapper.eq("type", Menu.TYPE_ITEM);
        return this.remove(removeQueryWrapper);
    }

    /**
     * 删除菜单组，仅限于删除空白组
     * @param id
     * @return
     */
    @Override
    public boolean removeGroup(Integer id) {
        QueryWrapper<Menu> removeQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(Menu.class);
        removeQueryWrapper.eq("id", id);
        removeQueryWrapper.eq("type", Menu.TYPE_GROUP);
        return this.remove(removeQueryWrapper);
    }

    /**
     * 获取给定菜单列表中的顶层部分
     * @param menuList
     * @return
     */
    private List<Menu> getLevelTopList(List<Menu> menuList) {
        return menuList
                .stream()
                .filter(item -> item.getParentId() == null)
                .collect(Collectors.toList());
    }

    /**
     * 汇总子菜单那列表，以父级id为索引
     * @param menuList
     * @return
     */
    private Map<Integer, List<Menu>> aggregateChildren(List<Menu> menuList) {
        return menuList
                .stream()
                .filter(item -> item.getParentId() != null)
                .collect(Collectors.groupingBy(item -> item.getParentId()));
    }
}
