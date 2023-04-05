package com.zjc.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjc.blog.dto.UserAdminDto;
import com.zjc.blog.dto.UserInfoDto;
import com.zjc.blog.entity.User;
import com.zjc.blog.vo.*;


public interface UserService extends IService<User> {

    User getByUsername(String username);

    UserInfoDto getUserInfoDtoById(Integer id);

    Page<UserAdminDto> userAdminDtoPage(PageVo pageVo, UserVo userVo);

    boolean updateUserAdminDto(UserAdminUpdateVo userAdminUpdateVo);

    boolean updateById(UserUpdateVo userUpdateVo);

    boolean updatePassword(UserPasswordVo userPasswordVo);

    boolean updateDeleted(EntityDeletedVo entityDeletedVo);

    boolean register(UserRegisterVo userRegisterVo);

}
