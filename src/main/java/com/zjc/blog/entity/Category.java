package com.zjc.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章分类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {
    /**
     * 分类名称
     */
    private String name;
}
