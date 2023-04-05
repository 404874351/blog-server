package com.zjc.blog.dto;

import lombok.Data;

import java.util.Date;

/**
 * 文章标签
 */
@Data
public class TagDto {
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
     * 标签下的文章数
     */
    private Integer articleCount;

}
