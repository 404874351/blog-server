package com.zjc.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.ArticleAdminDto;
import com.zjc.blog.dto.ArticleUpdateDto;
import com.zjc.blog.service.ArticleService;
import com.zjc.blog.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/admin/article")
public class AdminArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/page")
    public Page<ArticleAdminDto> page(@Validated PageVo pageVo, @Validated ArticleAdminVo articleAdminVo) {
        return articleService.articleAdminDtoPage(pageVo, articleAdminVo);
    }

    @GetMapping("/{id}")
    public ArticleUpdateDto one(@PathVariable("id") Integer id) {
        return articleService.getArticleUpdateDtoById(id);
    }

    @PostMapping("/save")
    public boolean save(@Validated ArticleSaveVo articleSaveVo) {
        return articleService.save(articleSaveVo);
    }

    @PostMapping("/remove/{id}")
    public boolean remove(@PathVariable("id") Integer id) {
        return articleService.removeById(id);
    }

    @PostMapping("/update/{id}")
    public boolean update(@Validated ArticleUpdateVo articleUpdateVo, @PathVariable("id") Integer id) {
        articleUpdateVo.setId(id);
        return articleService.updateById(articleUpdateVo);
    }

    @PostMapping("/update/{id}/deleted")
    public boolean updateDeleted(@Validated EntityDeletedVo entityDeletedVo, @PathVariable("id") Integer id) {
        entityDeletedVo.setId(id);
        return articleService.updateDeleted(entityDeletedVo);
    }
}
