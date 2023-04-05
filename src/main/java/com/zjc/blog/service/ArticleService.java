package com.zjc.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjc.blog.dto.ArticleAdminDto;
import com.zjc.blog.dto.ArticleDto;
import com.zjc.blog.dto.ArticleDashboardDto;
import com.zjc.blog.dto.ArticleUpdateDto;
import com.zjc.blog.entity.Article;
import com.zjc.blog.vo.*;

import java.util.List;

public interface ArticleService extends IService<Article> {

    Page<ArticleAdminDto> articleAdminDtoPage(PageVo pageVo, ArticleAdminVo articleAdminVo);

    Page<ArticleDto> articleDtoPage(PageVo pageVo, ArticleVo articleVo);

    Page<ArticleDashboardDto> articleDashboardDtoPage(PageVo pageVo);

    ArticleUpdateDto getArticleUpdateDtoById(Integer id);

    ArticleDto getArticleDtoById(Integer id, Integer userId);

    long sumViewCount();

    boolean save(ArticleSaveVo articleSaveVo);

    boolean view(Integer id);

    boolean praise(Integer articleId, Integer userId);

    boolean cancelPraise(Integer articleId, Integer userId);

    boolean updateById(ArticleUpdateVo articleUpdateVo);

    boolean updateDeleted(EntityDeletedVo entityDeletedVo);

    List<Integer> idListByTagName(String tagName);
}
