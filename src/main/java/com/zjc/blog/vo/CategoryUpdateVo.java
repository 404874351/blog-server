package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 分类更新 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 名称
     */
    @NotBlank(message = "分类名称不能为空")
    private String name;

}
