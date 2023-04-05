package com.zjc.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjc.blog.dto.CommentAdminDto;
import com.zjc.blog.dto.CommentDto;
import com.zjc.blog.entity.Comment;
import com.zjc.blog.vo.*;

public interface CommentService extends IService<Comment> {

    Page<CommentAdminDto> commentAdminDtoPage(PageVo pageVo, CommentAdminVo commentAdminVo);

    Page<CommentDto> commentDtoPage(PageVo pageVo, CommentVo commentVo);

    Page<CommentDto> childrenCommentDtoPage(PageVo pageVo, CommentVo commentVo);

    CommentDto getCommentDtoById(Integer id);

    Integer save(CommentSaveVo commentSaveVo);

    boolean praise(Integer commentId, Integer userId);

    boolean cancelPraise(Integer commentId, Integer userId);

    boolean updateById(CommentUpdateVo commentUpdateVo);

    boolean updateDeleted(EntityDeletedVo entityDeletedVo);

    boolean remove(Integer commentId, Integer userId);

}
