package com.zjc.blog.dto;

import com.zjc.blog.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 后台文章更新的展示数据
 */
@Data
public class ArticleUpdateDto {
    /**
     * id
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String description;
    /**
     * 封面图链接
     */
    private String coverUrl;
    /**
     * 原文链接
     */
    private String contentUrl;
    /**
     * 分类
     */
    private CategoryOptionDto category;
    /**
     * 标签列表
     */
    private List<TagOptionDto> tagList;
    /**
     * 置顶，0否，1是，默认0
     */
    private Integer top;
    /**
     * 关闭评论，0否，1是，默认0
     */
    private Integer closeComment;
}
