package com.zjc.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.ArticleDto;
import com.zjc.blog.service.ArticleService;
import com.zjc.blog.service.CommentService;
import com.zjc.blog.service.RelationUserArticleService;
import com.zjc.blog.service.RelationUserCommentService;
import com.zjc.blog.vo.ArticleVo;
import com.zjc.blog.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.zjc.blog.security.filter.JwtAuthenticationFilter.REQUEST_USER_ID_NAME;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RelationUserArticleService relationUserArticleService;

    @Autowired
    private RelationUserCommentService relationUserCommentService;

    /**
     * 获取文章统计数据
     * @return
     */
    @GetMapping("/statistic")
    public Map<String, Object> statistic() {
        Map<String, Object> map = new HashMap<>();
        map.put("count", articleService.count());
        map.put("viewCount", articleService.sumViewCount());
        map.put("praiseCount", relationUserArticleService.count());
        map.put("commentCount", commentService.count());

        return map;
    }

    /**
     * 分页获取文章
     * @return
     */
    @GetMapping("/page")
    public Page<ArticleDto> page(@Validated PageVo pageVo, @Validated ArticleVo articleVo) {
        return articleService.articleDtoPage(pageVo, articleVo);
    }

    /**
     * 获取单个文章
     * @return
     */
    @GetMapping("/{id}")
    public ArticleDto one(@PathVariable("id") Integer id, HttpServletRequest request) {
        articleService.view(id);
        return articleService.getArticleDtoById(id, (Integer) request.getAttribute(REQUEST_USER_ID_NAME));
    }

    /**
     * 点赞文章
     * @return
     */
    @PostMapping("/praise/{id}")
    public boolean praise(@PathVariable("id") Integer id, HttpServletRequest request) {
        return articleService.praise(id, (Integer) request.getAttribute(REQUEST_USER_ID_NAME));
    }

    /**
     * 取消点赞文章
     * @return
     */
    @PostMapping("/cancel_praise/{id}")
    public boolean cancelPraise(@PathVariable("id") Integer id, HttpServletRequest request) {
        return articleService.cancelPraise(id, (Integer) request.getAttribute(REQUEST_USER_ID_NAME));
    }

}
