package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuVo {
    /**
     * 菜单代码
     */
    private String code;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单路径
     */
    private String path;
}
