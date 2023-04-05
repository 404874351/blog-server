package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 评论 前台请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    /**
     * 文章id
     */
    @NotNull(message = "文章id不能为空")
    private Integer articleId;
    /**
     * 父级id
     */
    private Integer parentId;
    /**
     * 用户id，从登录态获取
     */
    private Integer userId;
    /**
     * 降序字段
     */
    private String sortBy;

}
