package com.zjc.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.MessageAdminDto;
import com.zjc.blog.dto.MessageDto;
import com.zjc.blog.entity.Comment;
import com.zjc.blog.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageMapper extends BaseMapper<Message> {

    Page<MessageAdminDto> selectMessageAdminDtoPage(
            Page<MessageAdminDto> page,
            @Param(Constants.WRAPPER) QueryWrapper<MessageAdminDto> queryWrapper
    );

    Page<MessageDto> selectMessageDtoPage(
            Page<MessageDto> page,
            @Param(Constants.WRAPPER) QueryWrapper<MessageDto> queryWrapper
    );

}
