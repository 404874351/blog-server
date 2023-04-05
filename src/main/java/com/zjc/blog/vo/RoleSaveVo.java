package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 角色新增 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleSaveVo {
    /**
     * 角色代码
     */
    @NotBlank(message = "角色代码不能为空")
    private String code;
    /**
     * 角色名
     */
    @NotBlank(message = "角色名不能为空")
    private String name;
    /**
     * 角色描述
     */
    private String description;

}
