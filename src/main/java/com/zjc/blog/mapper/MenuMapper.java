package com.zjc.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjc.blog.entity.Menu;
import com.zjc.blog.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<Menu> {


    List<Menu> selectListByUserId(Integer userId);
}
