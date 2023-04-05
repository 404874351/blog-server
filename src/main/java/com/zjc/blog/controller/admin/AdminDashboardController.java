package com.zjc.blog.controller.admin;

import com.zjc.blog.dto.ArticleDashboardDto;
import com.zjc.blog.dto.CategoryOptionDto;
import com.zjc.blog.dto.RoleDashboardDto;
import com.zjc.blog.dto.TagOptionDto;
import com.zjc.blog.service.*;
import com.zjc.blog.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/index")
    public Map<String, Object> index() {
        Map<String, Object> map = new HashMap<>();
        map.put("view", articleService.sumViewCount());
        map.put("article", articleService.count());
        map.put("user", userService.count());
        map.put("message", messageService.count());
        return map;
    }

    @GetMapping("/view")
    public List<ArticleDashboardDto> viewRank() {
        return articleService.articleDashboardDtoPage(new PageVo()).getRecords();
    }

    @GetMapping("/role")
    public List<RoleDashboardDto> roleCount() {
        return roleService.roleDashboardDtoList();
    }

    @GetMapping("/category")
    public List<CategoryOptionDto> categoryList() {
        return categoryService.categoryOptionDtoList();
    }

    @GetMapping("/tag")
    public List<TagOptionDto> tagList() {
        return tagService.tagOptionDtoList();
    }

}
