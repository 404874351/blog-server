package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 分类新增 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySaveVo {
    /**
     * 名称
     */
    @NotBlank(message = "分类名称不能为空")
    private String name;

}
