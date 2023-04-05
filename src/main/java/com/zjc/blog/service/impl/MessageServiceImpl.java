package com.zjc.blog.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.dto.MessageAdminDto;
import com.zjc.blog.dto.MessageDto;
import com.zjc.blog.entity.BaseEntity;
import com.zjc.blog.entity.Comment;
import com.zjc.blog.entity.Message;
import com.zjc.blog.mapper.CommentMapper;
import com.zjc.blog.mapper.MessageMapper;
import com.zjc.blog.service.CommentService;
import com.zjc.blog.service.MessageService;
import com.zjc.blog.utils.MyBatisUtils;
import com.zjc.blog.vo.EntityDeletedVo;
import com.zjc.blog.vo.MessageSaveVo;
import com.zjc.blog.vo.MessageVo;
import com.zjc.blog.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;


    @Override
    public Page<MessageAdminDto> messageAdminDtoPage(PageVo pageVo, MessageVo messageVo) {
        // 构造分页
        Page<MessageAdminDto> page = new Page<>();
        BeanUtil.copyProperties(pageVo, page);
        // 构造分页查询条件
        QueryWrapper<MessageAdminDto> pageQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(MessageAdminDto.class);
        pageQueryWrapper.like(!StrUtil.isEmpty(messageVo.getContent()), "message.content", messageVo.getContent());
        pageQueryWrapper.like(!StrUtil.isEmpty(messageVo.getNickname()), "user.nickname", messageVo.getNickname());
        // 查询数据
        return messageMapper.selectMessageAdminDtoPage(page, pageQueryWrapper);
    }

    @Override
    public Page<MessageDto> messageDtoPage(PageVo pageVo) {
        // 构造分页
        Page<MessageDto> page = new Page<>();
        BeanUtil.copyProperties(pageVo, page);
        // 构造分页查询条件
        QueryWrapper<MessageDto> pageQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(MessageDto.class);
        pageQueryWrapper.eq("message.deleted", BaseEntity.ENTITY_ACTIVED);
        pageQueryWrapper.orderByDesc("message.create_time");
        // 查询数据
        return messageMapper.selectMessageDtoPage(page, pageQueryWrapper);
    }

    @Override
    public boolean save(MessageSaveVo messageSaveVo) {
        Message message = new Message();
        BeanUtil.copyProperties(messageSaveVo, message);
        return this.save(message);
    }

    @Override
    public boolean updateDeleted(EntityDeletedVo entityDeletedVo) {
        Message message = new Message();
        BeanUtil.copyProperties(entityDeletedVo, message);
        return this.updateById(message);
    }

}
