package com.zjc.blog.dto;

import lombok.Data;

import java.util.Date;

/**
 * 留言
 */
@Data
public class MessageDto {
    /**
     * id
     */
    private Integer id;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户基本信息
     */
    private UserBaseInfoDto user;

}
