package com.zjc.blog.vo;

import com.zjc.blog.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 权限更新 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionUpdateVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 权限路径
     */
    private String url;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限类型
     */
    @NotNull(message = "权限类型不能为空")
    @Range(min = Permission.TYPE_ITEM, max = Permission.TYPE_GROUP, message = "权限类型仅限0或1")
    private Integer type;
    /**
     * 是否支持匿名访问
     */
    @Range(min = Permission.ANONYMOUS_DISABLE, max = Permission.ANONYMOUS_ENABLE, message = "匿名标志仅限0或1")
    private Integer anonymous;
}
