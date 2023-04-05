package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色分配菜单 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuVo {
    /**
     * 角色id
     */
    private Integer id;
    /**
     * 菜单id列表
     */
    @NotNull(message = "菜单id列表不能为空")
    private List<Integer> menuIdList;

}
