package com.zjc.blog.controller.admin;

import com.zjc.blog.dto.MenuTreeDto;
import com.zjc.blog.dto.MenuTreeOptionDto;
import com.zjc.blog.dto.UserMenuTreeDto;
import com.zjc.blog.entity.Menu;
import com.zjc.blog.service.MenuService;
import com.zjc.blog.vo.EntityDeletedVo;
import com.zjc.blog.vo.MenuSaveVo;
import com.zjc.blog.vo.MenuUpdateVo;
import com.zjc.blog.vo.MenuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.zjc.blog.security.filter.JwtAuthenticationFilter.REQUEST_USER_ID_NAME;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/admin/menu")
public class AdminMenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<MenuTreeDto> list(@Validated MenuVo menuVo) {
        return menuService.menuTreeDtoList(menuVo);
    }

    @GetMapping("/option")
    public List<MenuTreeOptionDto> option() {
        return menuService.menuTreeOptionDtoList();
    }

    @GetMapping("/user")
    public List<UserMenuTreeDto> userMenu(HttpServletRequest request) {
        return menuService.userMenuTreeDtoList((Integer) request.getAttribute(REQUEST_USER_ID_NAME));
    }

    @PostMapping("/save")
    public boolean save(@Validated MenuSaveVo menuSaveVo) {
        if (menuSaveVo.getType() == Menu.TYPE_GROUP) {
            return menuService.saveGroup(menuSaveVo);
        } else {
            return menuService.saveItem(menuSaveVo);
        }
    }

    @PostMapping("/remove/{id}")
    public boolean remove(@PathVariable("id") Integer id) {
        return menuService.removeById(id);
    }

    @PostMapping("/update/{id}")
    public boolean update(@Validated MenuUpdateVo menuUpdateVo, @PathVariable("id") Integer id) {
        menuUpdateVo.setId(id);
        return menuService.updateById(menuUpdateVo);
    }

    @PostMapping("/update/{id}/deleted")
    public boolean updateDeleted(@Validated EntityDeletedVo entityDeletedVo, @PathVariable("id") Integer id) {
        entityDeletedVo.setId(id);
        return menuService.updateDeleted(entityDeletedVo);
    }
}
