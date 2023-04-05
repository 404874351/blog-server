package com.zjc.blog.dto;

import lombok.Data;

import java.util.Date;

/**
 * 文章分类
 */
@Data
public class CategoryDto {
    /**
     * id
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否禁用
     */
    private Integer deleted;
    /**
     * 分类下的文章数
     */
    private Integer articleCount;

}
