package com.zjc.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjc.blog.dto.MessageAdminDto;
import com.zjc.blog.service.CommentService;
import com.zjc.blog.service.MessageService;
import com.zjc.blog.vo.EntityDeletedVo;
import com.zjc.blog.vo.MessageVo;
import com.zjc.blog.vo.PageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/admin/message")
public class AdminMessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/page")
    public Page<MessageAdminDto> page(@Validated PageVo pageVo, @Validated MessageVo messageVo) {
        return messageService.messageAdminDtoPage(pageVo, messageVo);
    }

    @PostMapping("/remove/{id}")
    public boolean remove(@PathVariable("id") Integer id) {
        return messageService.removeById(id);
    }


    @PostMapping("/update/{id}/deleted")
    public boolean updateDeleted(@Validated EntityDeletedVo entityDeletedVo, @PathVariable("id") Integer id) {
        entityDeletedVo.setId(id);
        return messageService.updateDeleted(entityDeletedVo);
    }


}
