package com.zjc.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjc.blog.dto.CategoryDto;
import com.zjc.blog.dto.CategoryOptionDto;
import com.zjc.blog.entity.Category;
import com.zjc.blog.vo.*;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<CategoryOptionDto> categoryOptionDtoList();

    List<CategoryOptionDto> queryByName(String name);

    Page<CategoryDto> categoryDtoPage(PageVo pageVo, CategoryVo categoryVo);

    CategoryOptionDto save(CategorySaveVo categorySaveVo);

    boolean updateById(CategoryUpdateVo categoryUpdateVo);

    boolean updateDeleted(EntityDeletedVo entityDeletedVo);

}
