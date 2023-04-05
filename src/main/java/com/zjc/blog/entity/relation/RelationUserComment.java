package com.zjc.blog.entity.relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关联 用户-评论，可用于检索点赞
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationUserComment {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 评论id
     */
    private Integer commentId;
}
