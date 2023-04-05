package com.zjc.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章标签
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseEntity {
    /**
     * 标签名称
     */
    private String name;
}
