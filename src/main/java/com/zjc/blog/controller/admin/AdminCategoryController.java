package com.zjc.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.CategoryDto;
import com.zjc.blog.dto.CategoryOptionDto;
import com.zjc.blog.service.CategoryService;
import com.zjc.blog.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public Page<CategoryDto> page(@Validated PageVo pageVo, @Validated CategoryVo categoryVo) {
        return categoryService.categoryDtoPage(pageVo, categoryVo);
    }

    @GetMapping("/query")
    public List<CategoryOptionDto> query(String name) {
        return categoryService.queryByName(name);
    }

    @GetMapping("/option")
    public List<CategoryOptionDto> option() {
        return categoryService.categoryOptionDtoList();
    }

    @PostMapping("/save")
    public CategoryOptionDto save(@Validated CategorySaveVo categorySaveVo) {
        CategoryOptionDto categoryOptionDto = categoryService.save(categorySaveVo);
        if (categoryOptionDto == null) {
            throw new RuntimeException("分类新增失败");
        }
        return categoryOptionDto;
    }

    @PostMapping("/remove/{id}")
    public boolean remove(@PathVariable("id") Integer id) {
        return categoryService.removeById(id);
    }

    @PostMapping("/update/{id}")
    public boolean update(@Validated CategoryUpdateVo categoryUpdateVo, @PathVariable("id") Integer id) {
        categoryUpdateVo.setId(id);
        return categoryService.updateById(categoryUpdateVo);
    }

    @PostMapping("/update/{id}/deleted")
    public boolean updateDeleted(@Validated EntityDeletedVo entityDeletedVo, @PathVariable("id") Integer id) {
        entityDeletedVo.setId(id);
        return categoryService.updateDeleted(entityDeletedVo);
    }


}
