package com.zjc.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjc.blog.entity.relation.RelationArticleTag;
import com.zjc.blog.entity.relation.RelationUserRole;

import java.util.List;

public interface RelationArticleTagService extends IService<RelationArticleTag> {

    boolean removeByArticleId(Integer articleId);

}
