package com.zjc.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.UserAdminDto;
import com.zjc.blog.service.UserService;
import com.zjc.blog.vo.EntityDeletedVo;
import com.zjc.blog.vo.PageVo;
import com.zjc.blog.vo.UserAdminUpdateVo;
import com.zjc.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    /**
     * 后台获取用户列表
     * @param pageVo
     * @param userVo
     * @return
     */
    @GetMapping("/page")
    public Page<UserAdminDto> page(@Validated PageVo pageVo, @Validated UserVo userVo) {
        return userService.userAdminDtoPage(pageVo, userVo);
    }

    /**
     * 后台更新用户信息
     * @param userAdminUpdateVo
     * @param id
     * @return
     */
    @PostMapping("/update/{id}")
    public boolean update(@Validated UserAdminUpdateVo userAdminUpdateVo, @PathVariable("id") Integer id) {
        userAdminUpdateVo.setId(id);
        return userService.updateUserAdminDto(userAdminUpdateVo);
    }

    /**
     * 后台更新用户禁用状态
     * @param entityDeletedVo
     * @param id
     * @return
     */
    @PostMapping("/update/{id}/deleted")
    public boolean updateDeleted(@Validated EntityDeletedVo entityDeletedVo, @PathVariable("id") Integer id) {
        entityDeletedVo.setId(id);
        return userService.updateDeleted(entityDeletedVo);
    }

    @PostMapping("/remove/{id}")
    public boolean remove(@PathVariable("id") Integer id) {
        return userService.removeById(id);
    }

}
