package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionVo {
    /**
     * 权限路径
     */
    private String url;
    /**
     * 权限名称
     */
    private String name;
}
