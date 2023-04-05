package com.zjc.blog.dto;

import lombok.Data;

import java.util.Date;

/**
 * 后台评论
 */
@Data
public class CommentAdminDto {
    /**
     * id
     */
    private Integer id;
    /**
     * 内容
     */
    private String content;
    /**
     * 置顶
     */
    private Integer top;
    /**
     * 文章id
     */
    private Integer articleId;
    /**
     * 文章标题
     */
    private String articleTitle;
    /**
     * 用户基本信息
     */
    private UserBaseInfoDto user;
    /**
     * 回复用户基本信息
     */
    private UserBaseInfoDto replyUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否禁用
     */
    private Integer deleted;

}
