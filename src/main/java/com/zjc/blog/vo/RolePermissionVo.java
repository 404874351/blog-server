package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

import java.util.List;

/**
 * 角色分配权限 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionVo {
    /**
     * 角色id
     */
    private Integer id;
    /**
     * 权限id列表
     */
    @NotNull(message = "权限id列表不能为空")
    private List<Integer> permissionIdList;

}
