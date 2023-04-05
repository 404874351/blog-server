package com.zjc.blog.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.dto.TagDto;
import com.zjc.blog.dto.TagOptionDto;
import com.zjc.blog.entity.Tag;
import com.zjc.blog.mapper.TagMapper;
import com.zjc.blog.service.TagService;
import com.zjc.blog.utils.MyBatisUtils;
import com.zjc.blog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;


    @Override
    public List<TagOptionDto> tagOptionDtoList() {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name");
        List<Tag> tagList = this.list(queryWrapper);
        return BeanUtil.copyToList(tagList, TagOptionDto.class);
    }

    @Override
    public List<TagOptionDto> queryByName(String name) {
        // 只查询10条记录
        Page<Tag> page = new Page<>(1, 10);
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name");
        queryWrapper.like(!StrUtil.isEmpty(name), "name", name);
        List<Tag> tagList = this.page(page, queryWrapper).getRecords();
        return BeanUtil.copyToList(tagList, TagOptionDto.class);
    }

    @Override
    public Page<TagDto> tagDtoPage(PageVo pageVo, TagVo tagVo) {
        // 构造分页
        Page<TagDto> page = new Page<>();
        BeanUtil.copyProperties(pageVo, page);
        // 构造分页查询条件
        QueryWrapper<TagDto> pageQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(TagDto.class);
        pageQueryWrapper.like(!StrUtil.isEmpty(tagVo.getName()), "name", tagVo.getName());
        // 查询数据
        return tagMapper.selectTagDtoPage(page, pageQueryWrapper);
    }

    @Override
    public TagOptionDto save(TagSaveVo tagSaveVo) {
        Tag tag = new Tag();
        BeanUtil.copyProperties(tagSaveVo, tag);
        boolean saveRes = this.save(tag);
        if (saveRes) {
            TagOptionDto tagOptionDto = new TagOptionDto();
            BeanUtil.copyProperties(tag, tagOptionDto);
            return tagOptionDto;
        }
        return null;
    }

    @Override
    public boolean updateById(TagUpdateVo tagUpdateVo) {
        Tag tag = new Tag();
        BeanUtil.copyProperties(tagUpdateVo, tag);
        return this.updateById(tag);
    }

    @Override
    public boolean updateDeleted(EntityDeletedVo entityDeletedVo) {
        Tag tag = new Tag();
        BeanUtil.copyProperties(entityDeletedVo, tag);
        return this.updateById(tag);
    }
}
