package com.zjc.blog.vo;

import com.zjc.blog.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 评论 更新 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 文章id，用于查验信息，不作为更新内容
     */
    @NotNull(message = "文章id不能为空")
    private Integer articleId;
    /**
     * 置顶
     */
    @Range(min = Comment.TOP_DISABLE, max = Comment.TOP_ENABLE, message = "置顶标志仅限0或1")
    private Integer top;

}
