package com.zjc.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.CommentAdminDto;
import com.zjc.blog.dto.CommentDto;
import com.zjc.blog.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    Page<CommentAdminDto> selectCommentAdminDtoPage(
            Page<CommentAdminDto> page,
            @Param(Constants.WRAPPER) QueryWrapper<CommentAdminDto> queryWrapper
    );

    Page<CommentDto> selectCommentDtoPage(
            Page<CommentDto> page,
            @Param("userId") Integer userId,
            @Param(Constants.WRAPPER) QueryWrapper<CommentDto> queryWrapper
    );

    List<CommentDto> selectChildrenCommentDtoList(
            @Param("userId") Integer userId,
            @Param(Constants.WRAPPER) QueryWrapper<CommentDto> queryWrapper
    );

    CommentDto selectCommentDtoById(Integer id);

}
