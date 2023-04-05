package com.zjc.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.MessageDto;
import com.zjc.blog.service.MessageService;
import com.zjc.blog.vo.MessageSaveVo;
import com.zjc.blog.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.zjc.blog.security.filter.JwtAuthenticationFilter.REQUEST_USER_ID_NAME;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/count")
    public long count() {
        return messageService.count();
    }

    @GetMapping("/page")
    public Page<MessageDto> page(@Validated PageVo pageVo) {
        return messageService.messageDtoPage(pageVo);
    }

    @PostMapping("/save")
    public boolean save(@Validated MessageSaveVo messageSaveVo, HttpServletRequest request) {
        messageSaveVo.setUserId((Integer) request.getAttribute(REQUEST_USER_ID_NAME));
        return messageService.save(messageSaveVo);
    }

}
