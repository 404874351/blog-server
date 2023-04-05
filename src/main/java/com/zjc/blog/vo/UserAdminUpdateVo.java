package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户后台更新 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAdminUpdateVo {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 角色id列表
     */
    @NotNull(message = "角色id列表不能为空")
    private List<Integer> roleIdList;

}
