package com.zjc.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.entity.relation.RelationUserArticle;
import com.zjc.blog.entity.relation.RelationUserComment;
import com.zjc.blog.mapper.RelationUserArticleMapper;
import com.zjc.blog.mapper.RelationUserCommentMapper;
import com.zjc.blog.service.RelationUserArticleService;
import com.zjc.blog.service.RelationUserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationUserCommentServiceImpl extends ServiceImpl<RelationUserCommentMapper, RelationUserComment> implements RelationUserCommentService {

    @Autowired
    private RelationUserCommentMapper relationUserCommentMapper;

}
