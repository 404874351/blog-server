package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAdminVo {
    /**
     * 标题
     */
    private String title;
    /**
     * 作者昵称
     */
    private String nickname;
    /**
     * 分类id
     */
    private Integer categoryId;
    /**
     * 标签id
     */
    private Integer tagId;

}
