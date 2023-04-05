package com.zjc.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章评论
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
    public static final int TOP_DISABLE = 0;
    public static final int TOP_ENABLE = 1;

    /**
     * 评论内容
     */
    private String content;
    /**
     * 置顶，0否，1是，默认0
     * 一篇文章只能有一个顶级评论被置顶
     */
    private Integer top;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 文章id
     */
    private Integer articleId;
    /**
     * 父评论id，顶级评论为空，用于检索二级评论
     */
    private Integer parentId;
    /**
     * 回复用户id，顶级评论为空，用于检索三级评论，但统一显示为二级
     */
    private Integer replyUserId;
}
