package com.zjc.blog.vo;

import com.zjc.blog.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * 禁用状态 请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityDeletedVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 逻辑删除标志
     */
    @Range(min = BaseEntity.ENTITY_ACTIVED, max = BaseEntity.ENTITY_DEACTIVATED, message = "禁用标志仅限0或1")
    private Integer deleted;

}
