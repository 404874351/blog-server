package com.zjc.blog.controller;

import com.zjc.blog.dto.CategoryOptionDto;
import com.zjc.blog.service.CategoryService;
import com.zjc.blog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/option")
    public List<CategoryOptionDto> option() {
        return categoryService.categoryOptionDtoList();
    }


}
