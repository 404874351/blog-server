package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论 后台请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentAdminVo {
    /**
     * 内容
     */
    private String content;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 文章标题
     */
    private String articleTitle;
    /**
     * 置顶
     */
    private Integer top;

}
