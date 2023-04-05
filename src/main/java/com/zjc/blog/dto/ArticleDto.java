package com.zjc.blog.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 前台文章
 */
@Data
public class ArticleDto {
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
     * 浏览量
     */
    private Integer viewCount;
    /**
     * 点赞量
     */
    private Integer praiseCount;
    /**
     * 评论量
     */
    private Integer commentCount;
    /**
     * 作者
     */
    private UserBaseInfoDto user;
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
    /**
     * 是否已点赞
     */
    private boolean praiseStatus;
    /**
     * 创建时间
     */
    private Date createTime;

}
