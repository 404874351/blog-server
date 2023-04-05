package com.zjc.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjc.blog.entity.relation.RelationArticleTag;
import com.zjc.blog.entity.relation.RelationUserArticle;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationArticleTagMapper extends BaseMapper<RelationArticleTag> {

}
