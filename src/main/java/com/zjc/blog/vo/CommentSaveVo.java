package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 评论新增 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveVo {
    /**
     * 内容
     */
    @NotBlank(message = "评论内容不能为空")
    private String content;
    /**
     * 文章id
     */
    @NotNull(message = "评论文章不能为空")
    private Integer articleId;
    /**
     * 用户id，从登录态获取
     */
    private Integer userId;
    /**
     * 父评论id，顶级评论为空
     */
    private Integer parentId;
    /**
     * 回复用户id，顶级评论为空
     */
    private Integer replyUserId;

}
