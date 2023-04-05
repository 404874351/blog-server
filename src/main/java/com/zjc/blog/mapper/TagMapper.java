package com.zjc.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.TagDto;
import com.zjc.blog.entity.Category;
import com.zjc.blog.entity.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TagMapper extends BaseMapper<Tag> {

    Page<TagDto> selectTagDtoPage(
            Page<TagDto> page,
            @Param(Constants.WRAPPER) QueryWrapper<TagDto> queryWrapper
    );

}
