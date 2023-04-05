package com.zjc.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zjc.blog.dto.ArticleAdminDto;
import com.zjc.blog.dto.ArticleDto;
import com.zjc.blog.dto.ArticleUpdateDto;
import com.zjc.blog.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    List<ArticleAdminDto> selectArticleAdminDtoPage(
            @Param("offset") Long offset,
            @Param("size") Long size,
            @Param("tagId") Integer tagId,
            @Param(Constants.WRAPPER) QueryWrapper<ArticleAdminDto> queryWrapper
    );

    long countArticleAdminDto(
            @Param(Constants.WRAPPER) QueryWrapper<ArticleAdminDto> queryWrapper
    );

    List<ArticleDto> selectArticleDtoPage(
            @Param("offset") Long offset,
            @Param("size") Long size,
            @Param(Constants.WRAPPER) QueryWrapper<ArticleDto> queryWrapper
    );

    long countArticleDto(
            @Param(Constants.WRAPPER) QueryWrapper<ArticleDto> queryWrapper
    );

    ArticleDto selectArticleDtoById(
            @Param("id") Integer id,
            @Param("userId") Integer userId);

    long sumViewCount();

    ArticleUpdateDto selectArticleUpdateDtoById(Integer id);

    List<Integer> selectIdListByTagName(@Param("tagName") String tagName);


}
