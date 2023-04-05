package com.zjc.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.entity.relation.RelationArticleTag;
import com.zjc.blog.entity.relation.RelationUserArticle;
import com.zjc.blog.mapper.RelationArticleTagMapper;
import com.zjc.blog.mapper.RelationUserArticleMapper;
import com.zjc.blog.service.RelationArticleTagService;
import com.zjc.blog.service.RelationUserArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationUserArticleServiceImpl extends ServiceImpl<RelationUserArticleMapper, RelationUserArticle> implements RelationUserArticleService {

    @Autowired
    private RelationUserArticleMapper relationUserArticleMapper;

}
