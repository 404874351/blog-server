package com.zjc.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {
    public static final int TOP_DISABLE = 0;
    public static final int TOP_ENABLE = 1;

    public static final int CLOSE_COMMENT_DISABLE = 0;
    public static final int CLOSE_COMMENT_ENABLE = 1;

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
     * 作者id
     */
    private Integer userId;
    /**
     * 分类id
     */
    private Integer categoryId;
    /**
     * 置顶，0否，1是，默认0
     */
    private Integer top;
    /**
     * 关闭评论，0否，1是，默认0
     */
    private Integer closeComment;

}
