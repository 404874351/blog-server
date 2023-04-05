package com.zjc.blog.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.dto.ArticleAdminDto;
import com.zjc.blog.dto.ArticleDto;
import com.zjc.blog.dto.ArticleDashboardDto;
import com.zjc.blog.dto.ArticleUpdateDto;
import com.zjc.blog.entity.Article;
import com.zjc.blog.entity.relation.RelationArticleTag;
import com.zjc.blog.entity.relation.RelationUserArticle;
import com.zjc.blog.mapper.ArticleMapper;
import com.zjc.blog.service.ArticleService;
import com.zjc.blog.service.RelationArticleTagService;
import com.zjc.blog.service.RelationUserArticleService;
import com.zjc.blog.utils.MyBatisUtils;
import com.zjc.blog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RelationArticleTagService relationArticleTagService;

    @Autowired
    private RelationUserArticleService relationUserArticleService;

    @Override
    public Page<ArticleAdminDto> articleAdminDtoPage(PageVo pageVo, ArticleAdminVo articleAdminVo) {
        // 构造分页
        Page<ArticleAdminDto> page = new Page<>();
        BeanUtil.copyProperties(pageVo, page);
        // 构造分页查询条件
        QueryWrapper<ArticleAdminDto> pageQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(ArticleAdminDto.class);
        pageQueryWrapper.like(!StrUtil.isEmpty(articleAdminVo.getTitle()), "article.title", articleAdminVo.getTitle());
        pageQueryWrapper.like(!StrUtil.isEmpty(articleAdminVo.getNickname()), "user.nickname", articleAdminVo.getNickname());
        pageQueryWrapper.eq(!BeanUtil.isEmpty(articleAdminVo.getCategoryId()), "article.category_id", articleAdminVo.getCategoryId());
        // 查询数据
        List<ArticleAdminDto> records = articleMapper.selectArticleAdminDtoPage(
                (page.getCurrent() - 1) * page.getSize(),
                page.getSize(),
                articleAdminVo.getTagId(),
                pageQueryWrapper
        );
        page.setRecords(records);

        // 构造总数查询条件
        QueryWrapper<ArticleAdminDto> countQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(ArticleAdminDto.class);
        countQueryWrapper.like(!StrUtil.isEmpty(articleAdminVo.getTitle()), "article.title", articleAdminVo.getTitle());
        countQueryWrapper.like(!StrUtil.isEmpty(articleAdminVo.getNickname()), "user.nickname", articleAdminVo.getNickname());
        countQueryWrapper.eq(!BeanUtil.isEmpty(articleAdminVo.getCategoryId()), "article.category_id", articleAdminVo.getCategoryId());
        countQueryWrapper.eq(!BeanUtil.isEmpty(articleAdminVo.getTagId()), "tag.id", articleAdminVo.getTagId());
        // 获取总数
        long total = articleMapper.countArticleAdminDto(countQueryWrapper);
        page.setTotal(total);
        // 获取总页数
        page.setPages((total / page.getSize()) + 1);

        return page;
    }

    @Override
    public Page<ArticleDto> articleDtoPage(PageVo pageVo, ArticleVo articleVo) {
        // 构造分页
        Page<ArticleDto> page = new Page<>();
        BeanUtil.copyProperties(pageVo, page);
        // 构造分页查询条件
        QueryWrapper<ArticleDto> pageQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(ArticleDto.class);
        // 置顶优先
        pageQueryWrapper.orderByDesc("top");
        // 用户查询条件
        pageQueryWrapper.eq(!BeanUtil.isEmpty(articleVo.getCategoryId()), "article.category_id", articleVo.getCategoryId());
        pageQueryWrapper.orderByDesc(!StrUtil.isEmpty(articleVo.getSortBy()), articleVo.getSortBy());
        pageQueryWrapper.and(!StrUtil.isEmpty(articleVo.getKey()), queryWrapper -> {
            List<Integer> idList = idListByTagName(articleVo.getKey());
            queryWrapper.like("article.title", articleVo.getKey())
                    .or().like("article.description", articleVo.getKey())
                    .or().like("category.name", articleVo.getKey())
                    .or().in(!CollUtil.isEmpty(idList), "article.id", idList);
        });
        // 查询数据
        List<ArticleDto> records = articleMapper.selectArticleDtoPage(
                (page.getCurrent() - 1) * page.getSize(),
                page.getSize(),
                pageQueryWrapper
        );
        page.setRecords(records);

        // 构造总数查询条件
        QueryWrapper<ArticleDto> countQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(ArticleDto.class);
        countQueryWrapper.eq(!BeanUtil.isEmpty(articleVo.getCategoryId()), "article.category_id", articleVo.getCategoryId());
        countQueryWrapper.and(!StrUtil.isEmpty(articleVo.getKey()), queryWrapper -> {
            queryWrapper.like("article.title", articleVo.getKey())
                    .or().like("article.description", articleVo.getKey())
                    .or().like("category.name", articleVo.getKey())
                    .or().like("tag.name", articleVo.getKey());
        });
        // 获取总数
        long total = articleMapper.countArticleDto(countQueryWrapper);
        page.setTotal(total);
        // 获取总页数
        page.setPages((total / page.getSize()) + 1);

        return page;
    }

    @Override
    public Page<ArticleDashboardDto> articleDashboardDtoPage(PageVo pageVo) {
        // 构造分页
        Page<Article> page = new Page<>();
        BeanUtil.copyProperties(pageVo, page);
        // 构造总数查询条件
        QueryWrapper<Article> queryWrapper = MyBatisUtils.createDefaultQueryWrapper(Article.class);
        queryWrapper.select("id", "title", "view_count");
        queryWrapper.orderByDesc("view_count");
        page = this.page(page, queryWrapper);
        Page<ArticleDashboardDto> articleDashboardDtoPage = new Page<>();
        articleDashboardDtoPage.setRecords(page.getRecords().stream().map(item -> {
            ArticleDashboardDto articleDashboardDto = new ArticleDashboardDto();
            BeanUtil.copyProperties(item, articleDashboardDto);
            return articleDashboardDto;
        }).collect(Collectors.toList()));
        return articleDashboardDtoPage;
    }

    @Override
    public ArticleUpdateDto getArticleUpdateDtoById(Integer id) {
        return articleMapper.selectArticleUpdateDtoById(id);
    }

    @Override
    public ArticleDto getArticleDtoById(Integer id, Integer userId) {
        return articleMapper.selectArticleDtoById(id, userId);
    }

    @Override
    public long sumViewCount() {
        return articleMapper.sumViewCount();
    }

    @Override
    public boolean save(ArticleSaveVo articleSaveVo) {
        // 保存基本信息
        Article article = new Article();
        BeanUtil.copyProperties(articleSaveVo, article);
        boolean saveRes = this.save(article);
        if (CollUtil.isEmpty(articleSaveVo.getTagIdList())) {
            return saveRes;
        }
        // 插入标签列表
        List<RelationArticleTag> relationRoleMenuList = articleSaveVo.getTagIdList().stream().map(item -> {
            RelationArticleTag relationArticleTag = new RelationArticleTag();
            relationArticleTag.setArticleId(article.getId());
            relationArticleTag.setTagId(item);
            return relationArticleTag;
        }).collect(Collectors.toList());
        boolean saveRelationRes = relationArticleTagService.saveBatch(relationRoleMenuList);

        return saveRes && saveRelationRes;
    }

    @Override
    public boolean view(Integer id) {
        UpdateWrapper<Article> updateWrapper = MyBatisUtils.createDefaultUpdateWrapper(Article.class);
        updateWrapper.eq("id", id);
        updateWrapper.setSql("view_count=view_count+1");
        return this.update(updateWrapper);
    }

    @Override
    public boolean praise(Integer articleId, Integer userId) {
        return relationUserArticleService.save(new RelationUserArticle(userId, articleId));
    }

    @Override
    public boolean cancelPraise(Integer articleId, Integer userId) {
        QueryWrapper<RelationUserArticle> queryWrapper = MyBatisUtils.createDefaultQueryWrapper(RelationUserArticle.class);
        queryWrapper.eq("article_id", articleId);
        queryWrapper.eq("user_id", userId);
        return relationUserArticleService.remove(queryWrapper);
    }

    @Override
    public boolean updateById(ArticleUpdateVo articleUpdateVo) {
        // 更新基本信息
        Article article = new Article();
        BeanUtil.copyProperties(articleUpdateVo, article);
        boolean updateRes = this.updateById(article);
        if (articleUpdateVo.getTagIdList() == null) {
            return updateRes;
        }
        // 删除原有权限列表
        boolean removeRes = relationArticleTagService.removeByArticleId(articleUpdateVo.getId());
        if (articleUpdateVo.getTagIdList().isEmpty()) {
            return updateRes && removeRes;
        }
        // 插入标签列表
        List<RelationArticleTag> relationRoleMenuList = articleUpdateVo.getTagIdList().stream().map(item -> {
            RelationArticleTag relationArticleTag = new RelationArticleTag();
            relationArticleTag.setArticleId(article.getId());
            relationArticleTag.setTagId(item);
            return relationArticleTag;
        }).collect(Collectors.toList());
        boolean saveRelationRes = relationArticleTagService.saveBatch(relationRoleMenuList);

        return updateRes && removeRes && saveRelationRes;
    }

    @Override
    public boolean updateDeleted(EntityDeletedVo entityDeletedVo) {
        Article article = new Article();
        BeanUtil.copyProperties(entityDeletedVo, article);
        return this.updateById(article);
    }

    @Override
    public List<Integer> idListByTagName(String tagName) {
        return articleMapper.selectIdListByTagName(tagName);
    }

}
