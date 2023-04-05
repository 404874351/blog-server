package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 留言 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageVo {
    /**
     * 名称
     */
    private String content;
    /**
     * 用户昵称
     */
    private String nickname;

}
