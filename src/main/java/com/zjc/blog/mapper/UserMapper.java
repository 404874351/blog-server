package com.zjc.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.UserAdminDto;
import com.zjc.blog.dto.UserInfoDto;
import com.zjc.blog.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    List<UserAdminDto> selectUserAdminDtoPage(
            @Param("offset") Long offset,
            @Param("size") Long size,
            @Param(Constants.WRAPPER) QueryWrapper<UserAdminDto> queryWrapper
    );

    UserInfoDto selectUserInfoDtoById(Integer id);

}
