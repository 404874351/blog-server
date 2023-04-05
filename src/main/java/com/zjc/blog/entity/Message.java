package com.zjc.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 留言
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message extends BaseEntity {
    /**
     * 留言内容
     */
    private String content;
    /**
      * 用户id，游客为空
     */
    private Integer userId;
}
