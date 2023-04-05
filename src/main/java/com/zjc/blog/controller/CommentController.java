package com.zjc.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.CommentDto;
import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.exception.ServiceException;
import com.zjc.blog.service.CommentService;
import com.zjc.blog.vo.CommentSaveVo;
import com.zjc.blog.vo.CommentVo;
import com.zjc.blog.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.zjc.blog.security.filter.JwtAuthenticationFilter.REQUEST_USER_ID_NAME;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/page")
    public Page<CommentDto> page(@Validated PageVo pageVo, @Validated CommentVo commentVo, HttpServletRequest request) {
        commentVo.setUserId((Integer) request.getAttribute(REQUEST_USER_ID_NAME));
        return commentService.commentDtoPage(pageVo, commentVo);
    }

    @PostMapping("/save")
    public CommentDto save(CommentSaveVo commentSaveVo, HttpServletRequest request) {
        commentSaveVo.setUserId((Integer) request.getAttribute(REQUEST_USER_ID_NAME));
        Integer id = commentService.save(commentSaveVo);
        if (id == null) {
            throw new ServiceException(StateCodeMsg.ACCESS_FAILED);
        }
        return commentService.getCommentDtoById(id);
    }

    /**
     * 点赞
     * @return
     */
    @PostMapping("/praise/{id}")
    public boolean praise(@PathVariable("id") Integer id, HttpServletRequest request) {
        return commentService.praise(id, (Integer) request.getAttribute(REQUEST_USER_ID_NAME));
    }

    /**
     * 取消点赞
     * @return
     */
    @PostMapping("/cancel_praise/{id}")
    public boolean cancelPraise(@PathVariable("id") Integer id, HttpServletRequest request) {
        return commentService.cancelPraise(id, (Integer) request.getAttribute(REQUEST_USER_ID_NAME));
    }

    @PostMapping("/remove/{id}")
    public boolean remove(@PathVariable("id") Integer id, HttpServletRequest request) {
        return commentService.remove(id, (Integer) request.getAttribute(REQUEST_USER_ID_NAME));
    }


}
