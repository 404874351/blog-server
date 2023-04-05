package com.zjc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjc.blog.entity.relation.RelationUserArticle;
import com.zjc.blog.entity.relation.RelationUserComment;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationUserCommentMapper extends BaseMapper<RelationUserComment> {

}
