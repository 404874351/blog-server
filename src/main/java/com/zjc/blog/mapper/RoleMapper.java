package com.zjc.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.RoleAdminDto;
import com.zjc.blog.dto.RoleDashboardDto;
import com.zjc.blog.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectListByUserId(Integer userId);

    List<RoleAdminDto> selectRoleAdminDtoPage(
            @Param("offset") Long offset,
            @Param("size") Long size,
            @Param(Constants.WRAPPER) QueryWrapper<RoleAdminDto> queryWrapper
    );

    List<RoleDashboardDto> selectRoleDashboardDtoList();

}
