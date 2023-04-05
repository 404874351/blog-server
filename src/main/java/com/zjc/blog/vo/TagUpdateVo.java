package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 标签更新 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagUpdateVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 名称
     */
    @NotBlank(message = "标签名称不能为空")
    private String name;

}
