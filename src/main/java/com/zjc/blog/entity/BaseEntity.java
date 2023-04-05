package com.zjc.blog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.util.Date;

@Data
public abstract class BaseEntity {
    /**
     * 数据激活状态
     */
    public static final int ENTITY_ACTIVED = 0;
    /**
     * 数据停用状态
     */
    public static final int ENTITY_DEACTIVATED = 1;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 逻辑删除标志
     */
//    @TableLogic
    private Integer deleted;

    /**
     * 检查记录是否停用
     * @return
     */
    public boolean isEntityDeactivated() {
        return this.deleted == ENTITY_DEACTIVATED;
    }
}
