package com.zjc.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.TagDto;
import com.zjc.blog.dto.TagOptionDto;
import com.zjc.blog.service.TagService;
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
@RequestMapping("/admin/tag")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/page")
    public Page<TagDto> page(@Validated PageVo pageVo, @Validated TagVo tagVo) {
        return tagService.tagDtoPage(pageVo, tagVo);
    }

    @GetMapping("/query")
    public List<TagOptionDto> query(String name) {
        return tagService.queryByName(name);
    }

    @GetMapping("/option")
    public List<TagOptionDto> option() {
        return tagService.tagOptionDtoList();
    }

    @PostMapping("/save")
    public TagOptionDto save(@Validated TagSaveVo tagSaveVo) {
        TagOptionDto tagOptionDto = tagService.save(tagSaveVo);
        if (tagOptionDto == null) {
            throw new RuntimeException("新增失败");
        }
        return tagOptionDto;
    }

    @PostMapping("/remove/{id}")
    public boolean remove(@PathVariable("id") Integer id) {
        return tagService.removeById(id);
    }

    @PostMapping("/update/{id}")
    public boolean update(@Validated TagUpdateVo tagUpdateVo, @PathVariable("id") Integer id) {
        tagUpdateVo.setId(id);
        return tagService.updateById(tagUpdateVo);
    }

    @PostMapping("/update/{id}/deleted")
    public boolean updateDeleted(@Validated EntityDeletedVo entityDeletedVo, @PathVariable("id") Integer id) {
        entityDeletedVo.setId(id);
        return tagService.updateDeleted(entityDeletedVo);
    }


}
