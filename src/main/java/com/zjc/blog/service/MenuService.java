package com.zjc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjc.blog.dto.MenuTreeDto;
import com.zjc.blog.dto.MenuTreeOptionDto;
import com.zjc.blog.dto.UserMenuTreeDto;
import com.zjc.blog.entity.Menu;
import com.zjc.blog.vo.EntityDeletedVo;
import com.zjc.blog.vo.MenuSaveVo;
import com.zjc.blog.vo.MenuUpdateVo;
import com.zjc.blog.vo.MenuVo;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<MenuTreeDto> menuTreeDtoList(MenuVo menuVo);

    List<MenuTreeOptionDto> menuTreeOptionDtoList();

    List<UserMenuTreeDto> userMenuTreeDtoList(Integer userId);

    boolean saveItem(MenuSaveVo menuSaveVo);

    boolean saveGroup(MenuSaveVo menuSaveVo);

    boolean updateById(MenuUpdateVo menuUpdateVo);

    boolean updateDeleted(EntityDeletedVo entityDeletedVo);

    boolean removeItem(Integer id);

    boolean removeGroup(Integer id);

}
