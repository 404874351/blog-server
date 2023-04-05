package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章 前台请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {
    /**
     * 搜索关键词
     */
    private String key;
    /**
     * 分类id
     */
    private Integer categoryId;
    /**
     * 降序字段
     */
    private String sortBy;

}
