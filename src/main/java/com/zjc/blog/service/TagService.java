package com.zjc.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjc.blog.dto.TagDto;
import com.zjc.blog.dto.TagOptionDto;
import com.zjc.blog.entity.Tag;
import com.zjc.blog.vo.*;

import java.util.List;

public interface TagService extends IService<Tag> {

    List<TagOptionDto> tagOptionDtoList();

    List<TagOptionDto> queryByName(String name);

    Page<TagDto> tagDtoPage(PageVo pageVo, TagVo tagVo);

    TagOptionDto save(TagSaveVo tagSaveVo);

    boolean updateById(TagUpdateVo tagUpdateVo);

    boolean updateDeleted(EntityDeletedVo entityDeletedVo);
}
