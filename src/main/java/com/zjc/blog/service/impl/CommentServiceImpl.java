package com.zjc.blog.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.dto.CommentAdminDto;
import com.zjc.blog.dto.CommentDto;
import com.zjc.blog.entity.Comment;
import com.zjc.blog.entity.relation.RelationUserComment;
import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.exception.ServiceException;
import com.zjc.blog.mapper.CommentMapper;
import com.zjc.blog.service.CommentService;
import com.zjc.blog.service.RelationUserCommentService;
import com.zjc.blog.utils.MyBatisUtils;
import com.zjc.blog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RelationUserCommentService relationUserCommentService;

    @Override
    public Page<CommentAdminDto> commentAdminDtoPage(PageVo pageVo, CommentAdminVo commentAdminVo) {
        // 构造分页
        Page<CommentAdminDto> page = new Page<>();
        BeanUtil.copyProperties(pageVo, page);
        // 构造分页查询条件
        QueryWrapper<CommentAdminDto> pageQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(CommentAdminDto.class);
        pageQueryWrapper.like(!StrUtil.isEmpty(commentAdminVo.getContent()), "comment.content", commentAdminVo.getContent());
        pageQueryWrapper.like(!StrUtil.isEmpty(commentAdminVo.getNickname()), "comment_user.nickname", commentAdminVo.getNickname());
        pageQueryWrapper.like(!StrUtil.isEmpty(commentAdminVo.getArticleTitle()), "article.title", commentAdminVo.getArticleTitle());
        pageQueryWrapper.eq(!BeanUtil.isEmpty(commentAdminVo.getTop()), "comment.top", commentAdminVo.getTop());
        // 查询数据
        return commentMapper.selectCommentAdminDtoPage(page, pageQueryWrapper);
    }

    @Override
    public Page<CommentDto> commentDtoPage(PageVo pageVo, CommentVo commentVo) {
        if (commentVo.getParentId() != null) {
            return childrenCommentDtoPage(pageVo, commentVo);
        }
        // 构造分页
        Page<CommentDto> page = new Page<>();
        BeanUtil.copyProperties(pageVo, page);
        // 构造分页查询条件
        QueryWrapper<CommentDto> pageQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(CommentDto.class);
        pageQueryWrapper.eq("comment.article_id", commentVo.getArticleId());
        pageQueryWrapper.isNull("comment.parent_id");
        pageQueryWrapper.orderByDesc("comment.top");
        pageQueryWrapper.orderByDesc(!StrUtil.isEmpty(commentVo.getSortBy()), commentVo.getSortBy());
        // 查询父评论
        Page<CommentDto> parentPage = commentMapper.selectCommentDtoPage(page, commentVo.getUserId(), pageQueryWrapper);
        if (CollUtil.isEmpty(parentPage.getRecords())) {
            return parentPage;
        }
        // 查询子评论节选列表
        List<Integer> parentIdList = parentPage.getRecords().stream().map(item -> item.getId()).collect(Collectors.toList());
        QueryWrapper<CommentDto> childrenQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(CommentDto.class);
        childrenQueryWrapper.in("comment.parent_id", parentIdList);
        List<CommentDto> childrenList = commentMapper.selectChildrenCommentDtoList(commentVo.getUserId(), childrenQueryWrapper);
        // 封装子评论
        Map<Integer, List<CommentDto>> childrenMap = childrenList.stream().collect(Collectors.groupingBy(item -> item.getParentId()));
        parentPage.getRecords().forEach(item -> {
            item.setChildren(childrenMap.get(item.getId()));
        });

        return parentPage;
    }

    @Override
    public Page<CommentDto> childrenCommentDtoPage(PageVo pageVo, CommentVo commentVo) {
        if (commentVo.getParentId() == null) {
            return commentDtoPage(pageVo, commentVo);
        }
        // 构造分页
        Page<CommentDto> page = new Page<>();
        BeanUtil.copyProperties(pageVo, page);
        // 构造分页查询条件
        QueryWrapper<CommentDto> pageQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(CommentDto.class);
        pageQueryWrapper.eq("comment.article_id", commentVo.getArticleId());
        pageQueryWrapper.eq("comment.parent_id", commentVo.getParentId());
        pageQueryWrapper.orderByAsc("comment.create_time");
        return commentMapper.selectCommentDtoPage(page, commentVo.getUserId(), pageQueryWrapper);
    }

    @Override
    public CommentDto getCommentDtoById(Integer id) {
        return commentMapper.selectCommentDtoById(id);
    }

    @Override
    public Integer save(CommentSaveVo commentSaveVo) {
        Comment comment = new Comment();
        BeanUtil.copyProperties(commentSaveVo, comment);
        if (this.save(comment)) {
            return comment.getId();
        }
        return null;
    }

    @Override
    public boolean praise(Integer commentId, Integer userId) {
        return relationUserCommentService.save(new RelationUserComment(userId, commentId));
    }

    @Override
    public boolean cancelPraise(Integer commentId, Integer userId) {
        QueryWrapper<RelationUserComment> queryWrapper = MyBatisUtils.createDefaultQueryWrapper(RelationUserComment.class);
        queryWrapper.eq("comment_id", commentId);
        queryWrapper.eq("user_id", userId);
        return relationUserCommentService.remove(queryWrapper);
    }

    @Override
    public boolean updateById(CommentUpdateVo commentUpdateVo) {
        Comment comment = new Comment();
        BeanUtil.copyProperties(commentUpdateVo, comment);
        // 不更新articleId
        comment.setArticleId(null);
        // 如需更新置顶字段，则必须保证该文章下只有唯一顶级评论可置顶
        if (comment.getTop() == Comment.TOP_ENABLE) {
            QueryWrapper<Comment> queryWrapper = MyBatisUtils.createDefaultQueryWrapper(Comment.class);
            queryWrapper.eq("article_id", commentUpdateVo.getArticleId());
            queryWrapper.eq("top", Comment.TOP_ENABLE);
            // 置顶评论必唯一
            if (this.getOne(queryWrapper) != null) {
                throw new ServiceException(StateCodeMsg.COMMENT_TOP_EXIST);
            }
            // 置顶评论必顶级
            if (this.getById(commentUpdateVo.getId()).getParentId() != null) {
                throw new ServiceException(StateCodeMsg.COMMENT_NOT_LEVEL_TOP);
            }
        }
        return this.updateById(comment);
    }

    @Override
    public boolean updateDeleted(EntityDeletedVo entityDeletedVo) {
        Comment comment = new Comment();
        BeanUtil.copyProperties(entityDeletedVo, comment);
        return this.updateById(comment);
    }

    @Override
    public boolean remove(Integer commentId, Integer userId) {
        QueryWrapper<Comment> queryWrapper = MyBatisUtils.createDefaultQueryWrapper(Comment.class);
        queryWrapper.eq("id", commentId);
        queryWrapper.eq("user_id", userId);
        return this.remove(queryWrapper);
    }

}
