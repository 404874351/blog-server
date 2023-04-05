package com.zjc.blog.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.dto.CategoryDto;
import com.zjc.blog.dto.CategoryOptionDto;
import com.zjc.blog.entity.Category;
import com.zjc.blog.mapper.CategoryMapper;
import com.zjc.blog.service.CategoryService;
import com.zjc.blog.utils.MyBatisUtils;
import com.zjc.blog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<CategoryOptionDto> categoryOptionDtoList() {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name");
        List<Category> categoryList = this.list(queryWrapper);
        return BeanUtil.copyToList(categoryList, CategoryOptionDto.class);
    }

    @Override
    public List<CategoryOptionDto> queryByName(String name) {
        // 只查询10条记录
        Page<Category> page = new Page<>(1, 10);
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name");
        queryWrapper.like(!StrUtil.isEmpty(name), "name", name);
        List<Category> categoryList = this.page(page, queryWrapper).getRecords();
        return BeanUtil.copyToList(categoryList, CategoryOptionDto.class);
    }

    @Override
    public Page<CategoryDto> categoryDtoPage(PageVo pageVo, CategoryVo categoryVo) {
        // 构造分页
        Page<CategoryDto> page = new Page<>();
        BeanUtil.copyProperties(pageVo, page);
        // 构造分页查询条件
        QueryWrapper<CategoryDto> pageQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(CategoryDto.class);
        pageQueryWrapper.like(!StrUtil.isEmpty(categoryVo.getName()), "name", categoryVo.getName());
        // 查询数据
        return categoryMapper.selectCategoryDtoPage(page, pageQueryWrapper);
    }

    @Override
    public CategoryOptionDto save(CategorySaveVo categorySaveVo) {
        Category category = new Category();
        BeanUtil.copyProperties(categorySaveVo, category);
        boolean saveRes = this.save(category);
        if (saveRes) {
            CategoryOptionDto categoryOptionDto = new CategoryOptionDto();
            BeanUtil.copyProperties(category, categoryOptionDto);
            return categoryOptionDto;
        }
        return null;
    }

    @Override
    public boolean updateById(CategoryUpdateVo categoryUpdateVo) {
        Category category = new Category();
        BeanUtil.copyProperties(categoryUpdateVo, category);
        return this.updateById(category);
    }

    @Override
    public boolean updateDeleted(EntityDeletedVo entityDeletedVo) {
        Category category = new Category();
        BeanUtil.copyProperties(entityDeletedVo, category);
        return this.updateById(category);
    }
}
