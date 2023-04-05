package com.zjc.blog.entity.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关联 用户-文章，可用于检索点赞
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationUserArticle {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 文章id
     */
    private Integer articleId;
}
