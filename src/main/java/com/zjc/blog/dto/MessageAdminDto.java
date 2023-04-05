package com.zjc.blog.dto;

import lombok.Data;

import java.util.Date;

/**
 * 后台留言
 */
@Data
public class MessageAdminDto {
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
     * 是否禁用
     */
    private Integer deleted;
    /**
     * 用户基本信息
     */
    private UserBaseInfoDto user;

}
