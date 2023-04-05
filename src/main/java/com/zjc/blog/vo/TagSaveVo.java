package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 标签新增 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagSaveVo {
    /**
     * 名称
     */
    @NotBlank(message = "标签名称不能为空")
    private String name;

}
