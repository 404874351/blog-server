package com.zjc.blog.entity.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关联 文章-标签
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationArticleTag {
    /**
     * 文章id
     */
    private Integer articleId;
    /**
     * 标签id
     */
    private Integer tagId;
}
