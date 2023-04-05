package com.zjc.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.CommentAdminDto;
import com.zjc.blog.service.CommentService;
import com.zjc.blog.vo.CommentUpdateVo;
import com.zjc.blog.vo.CommentAdminVo;
import com.zjc.blog.vo.EntityDeletedVo;
import com.zjc.blog.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/admin/comment")
public class AdminCommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/page")
    public Page<CommentAdminDto> page(@Validated PageVo pageVo, @Validated CommentAdminVo commentAdminVo) {
        return commentService.commentAdminDtoPage(pageVo, commentAdminVo);
    }

    @PostMapping("/remove/{id}")
    public boolean remove(@PathVariable("id") Integer id) {
        return commentService.removeById(id);
    }


    @PostMapping("/update/{id}")
    public boolean update(@Validated CommentUpdateVo commentUpdateVo, @PathVariable("id") Integer id) {
        commentUpdateVo.setId(id);
        return commentService.updateById(commentUpdateVo);
    }

    @PostMapping("/update/{id}/deleted")
    public boolean updateDeleted(@Validated EntityDeletedVo entityDeletedVo, @PathVariable("id") Integer id) {
        entityDeletedVo.setId(id);
        return commentService.updateDeleted(entityDeletedVo);
    }


}
