package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 留言 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageSaveVo {
    /**
     * 内容
     */
    @NotBlank(message = "留言内容不能为空")
    private String content;
    /**
     * 用户id
     */
    private Integer userId;

}
