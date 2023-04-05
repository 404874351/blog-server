package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色更新 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 角色代码
     */
    private String code;
    /**
     * 角色名
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;

}
