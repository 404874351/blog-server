package com.zjc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.entity.relation.RelationArticleTag;
import com.zjc.blog.entity.relation.RelationUserRole;
import com.zjc.blog.mapper.RelationArticleTagMapper;
import com.zjc.blog.mapper.RelationUserRoleMapper;
import com.zjc.blog.service.RelationArticleTagService;
import com.zjc.blog.service.RelationUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationArticleTagServiceImpl extends ServiceImpl<RelationArticleTagMapper, RelationArticleTag> implements RelationArticleTagService {

    @Autowired
    private RelationArticleTagMapper relationArticleTagMapper;

    @Override
    public boolean removeByArticleId(Integer articleId) {
        QueryWrapper<RelationArticleTag> removeQueryWrapper = new QueryWrapper<>();
        removeQueryWrapper.eq("article_id", articleId);
        this.remove(removeQueryWrapper);
        // 无论删除几条数据，只要不报错即可
        return true;
    }

}
