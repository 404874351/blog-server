package com.zjc.blog.controller;

import com.zjc.blog.service.ArticleService;
import com.zjc.blog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;


}
