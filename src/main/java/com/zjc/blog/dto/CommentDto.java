package com.zjc.blog.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 前台评论
 */
@Data
public class CommentDto {
    /**
     * id
     */
    private Integer id;
    /**
     * 内容
     */
    private String content;
    /**
     * 父级id
     */
    private Integer parentId;
    /**
     * 置顶
     */
    private Integer top;
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
     * 点赞数
     */
    private long praiseCount;
    /**
     * 是否已点赞
     */
    private boolean praiseStatus;
    /**
     * 子评论总数
     */
    private long childrenCount;
    /**
     * 子评论部分列表，默认最多加载3条
     */
    private List<CommentDto> children;


}
