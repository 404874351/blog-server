package com.zjc.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

/**
 * 分页 请求对象
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {
    /**
     * 当前页，默认从1开始
     */
    private Integer current = 1;

    /**
     * 页面大小，默认10
     */
    private Integer size = 10;

    public void setCurrent(Integer current) {
        this.current = current > 0 ? current : 1;
    }

    public void setSize(Integer size) {
        this.size = size > 0 ? size : 10;
    }
}
