package com.zjc.blog.vo;

import com.zjc.blog.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 分类更新 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String description;
    /**
     * 封面图链接
     */
    private String coverUrl;
    /**
     * 分类id
     */
    private Integer categoryId;
    /**
     * 标签id列表
     */
    private List<Integer> tagIdList;
    /**
     * 置顶
     */
    @Range(min = Article.TOP_DISABLE, max = Article.TOP_ENABLE, message = "置顶类型仅限0或1")
    private Integer top;
    /**
     * 关闭评论
     */
    @Range(min = Article.CLOSE_COMMENT_DISABLE, max = Article.CLOSE_COMMENT_ENABLE, message = "关闭评论标志仅限0或1")
    private Integer closeComment;

}
