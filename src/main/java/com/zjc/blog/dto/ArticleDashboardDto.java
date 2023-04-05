package com.zjc.blog.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 仪表盘文章信息
 */
@Data
public class ArticleDashboardDto {
    /**
     * id
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 浏览量
     */
    private Integer viewCount;
}
