package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * 文章新增 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSaveVo {
    /**
     * 标题
     */
    @NotBlank(message = "文章标题不能为空")
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
     * 原文链接
     */
    @NotBlank(message = "文章内容不能为空")
    private String contentUrl;
    /**
     * 作者id
     */
    @NotNull(message = "文章作者不能为空")
    private Integer userId;
    /**
     * 分类id
     */
    @NotNull(message = "文章分类不能为空")
    private Integer categoryId;
    /**
     * 标签id列表
     */
    private List<Integer> tagIdList;

}
