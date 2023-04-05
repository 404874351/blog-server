package com.zjc.blog.vo;

import com.zjc.blog.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 菜单更新 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuUpdateVo {
    /**
     * id
     */
    private Integer id;
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
    /**
     * 菜单组件
     */
    private String component;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单类型
     */
    @NotNull(message = "菜单类型不能为空")
    @Range(min = Menu.TYPE_ITEM, max = Menu.TYPE_GROUP, message = "菜单类型仅限0或1")
    private Integer type;
    /**
     * 是否隐藏
     */
    @Range(min = Menu.HIDDEN_DISABLE, max = Menu.HIDDEN_ENABLE, message = "隐藏标志仅限0或1")
    private Integer hidden;
}
