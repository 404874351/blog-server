package com.zjc.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjc.blog.dto.MessageAdminDto;
import com.zjc.blog.dto.MessageDto;
import com.zjc.blog.entity.Comment;
import com.zjc.blog.entity.Message;
import com.zjc.blog.vo.EntityDeletedVo;
import com.zjc.blog.vo.MessageSaveVo;
import com.zjc.blog.vo.MessageVo;
import com.zjc.blog.vo.PageVo;

public interface MessageService extends IService<Message> {

    Page<MessageAdminDto> messageAdminDtoPage(PageVo pageVo, MessageVo messageVo);

    Page<MessageDto> messageDtoPage(PageVo pageVo);

    boolean save(MessageSaveVo messageSaveVo);

    boolean updateDeleted(EntityDeletedVo entityDeletedVo);
}
