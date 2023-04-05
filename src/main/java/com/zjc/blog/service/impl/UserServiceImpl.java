package com.zjc.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjc.blog.dto.UserAdminDto;
import com.zjc.blog.dto.UserInfoDto;
import com.zjc.blog.entity.Role;
import com.zjc.blog.entity.User;
import com.zjc.blog.entity.relation.RelationUserRole;
import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.exception.ServiceException;
import com.zjc.blog.mapper.UserMapper;
import com.zjc.blog.service.RelationUserRoleService;
import com.zjc.blog.service.UserService;
import com.zjc.blog.utils.MyBatisUtils;
import com.zjc.blog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RelationUserRoleService relationUserRoleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    @Override
    public User getByUsername(String username) {
        return this.getOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public UserInfoDto getUserInfoDtoById(Integer id) {
        return userMapper.selectUserInfoDtoById(id);
    }

    /**
     * 分页获取后台用户
     * @return
     */
    @Override
    public Page<UserAdminDto> userAdminDtoPage(PageVo pageVo, UserVo userVo) {
        // 构造分页
        Page<UserAdminDto> page = new Page<>();
        BeanUtil.copyProperties(pageVo, page);
        // 构造分页查询条件
        QueryWrapper<UserAdminDto> pageQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(UserAdminDto.class);
        pageQueryWrapper.like(!StrUtil.isEmpty(userVo.getNickname()), "nickname", userVo.getNickname());
        pageQueryWrapper.like(!StrUtil.isEmpty(userVo.getPhone()), "phone", userVo.getPhone());
        // 查询数据
        List<UserAdminDto> records = userMapper.selectUserAdminDtoPage(
                (page.getCurrent() - 1) * page.getSize(),
                page.getSize(),
                pageQueryWrapper
        );
        page.setRecords(records);

        // 构造总数查询条件
        QueryWrapper<User> countQueryWrapper = MyBatisUtils.createDefaultQueryWrapper(User.class);
        countQueryWrapper.like(!StrUtil.isEmpty(userVo.getNickname()), "nickname", userVo.getNickname());
        countQueryWrapper.like(!StrUtil.isEmpty(userVo.getPhone()), "phone", userVo.getPhone());
        // 获取总数
        long total = this.count(countQueryWrapper);
        page.setTotal(total);
        // 获取总页数
        page.setPages((total / page.getSize()) + 1);

        return page;
    }

    /**
     * 更新后台用户更新，仅支持更新昵称和角色列表
     * 操作完成后，建议将用户下线
     * @param userAdminUpdateVo
     * @return
     */
    @Override
    public boolean updateUserAdminDto(UserAdminUpdateVo userAdminUpdateVo) {
        // 更新非角色列表部分
        User user = new User();
        user.setId(userAdminUpdateVo.getId());
        user.setNickname(userAdminUpdateVo.getNickname());
        boolean updateRes = this.updateById(user);
        if (CollUtil.isEmpty(userAdminUpdateVo.getRoleIdList())) {
            return updateRes;
        }
        // 删除原有角色列表
        boolean removeRes = relationUserRoleService.removeByUserId(userAdminUpdateVo.getId());
        // 插入新角色列表
        List<RelationUserRole> relationUserRoleList = userAdminUpdateVo.getRoleIdList().stream().map(item -> {
            RelationUserRole relationUserRole = new RelationUserRole();
            relationUserRole.setUserId(userAdminUpdateVo.getId());
            relationUserRole.setRoleId(item);
            return relationUserRole;
        }).collect(Collectors.toList());
        boolean saveRes = relationUserRoleService.saveBatch(relationUserRoleList);

        return updateRes && removeRes && saveRes;
    }

    /**
     * 修改用户信息，一般用于用户自身操作
     * @param userUpdateVo
     * @return
     */
    @Override
    public boolean updateById(UserUpdateVo userUpdateVo) {
        // 检查手机号是否重合
        if (!StrUtil.isEmpty(userUpdateVo.getPhone())) {
            QueryWrapper<User> queryWrapper = MyBatisUtils.createDefaultQueryWrapper(User.class);
            queryWrapper.eq("phone", userUpdateVo.getPhone());
            queryWrapper.ne("id", userUpdateVo.getId());
            if (getOne(queryWrapper) != null) {
                throw new ServiceException(StateCodeMsg.PHONE_EXIST);
            }
        }
        // 更新数据
        User user = new User();
        BeanUtil.copyProperties(userUpdateVo, user);
        user.setUsername(user.getPhone());
        return this.updateById(user);
    }

    /**
     * 修改密码
     * @param userPasswordVo
     * @return
     */
    @Override
    public boolean updatePassword(UserPasswordVo userPasswordVo) {
        // 检查原密码
        User user = this.getById(userPasswordVo.getId());
        if (user == null) {
            throw new ServiceException(StateCodeMsg.USER_NOT_EXIST);
        }
        if (!bCryptPasswordEncoder.matches(userPasswordVo.getPassword(), user.getPassword())) {
            throw new ServiceException(StateCodeMsg.RAW_PASSWORD_ERROR);
        }
        // 替换新密码
        UpdateWrapper<User> updateWrapper = MyBatisUtils.createDefaultUpdateWrapper(User.class);
        updateWrapper.set("password", bCryptPasswordEncoder.encode(userPasswordVo.getNewPassword()));
        updateWrapper.eq("id", userPasswordVo.getId());
        return this.update(updateWrapper);
    }

    /**
     * 更新用户禁用状态
     * 操作完成后，建议将用户下线
     * @param entityDeletedVo
     * @return
     */
    @Override
    public boolean updateDeleted(EntityDeletedVo entityDeletedVo) {
        User user = new User();
        BeanUtil.copyProperties(entityDeletedVo, user);
        return this.updateById(user);
    }

    /**
     * 用户注册，默认分配普通用户角色
     * @param userRegisterVo
     * @return
     */
    @Override
    public boolean register(UserRegisterVo userRegisterVo) {
        // 添加用户，密码加密
        User user = new User();
        BeanUtil.copyProperties(userRegisterVo, user);
        user.setUsername(user.getPhone());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        boolean saveUserRes = this.save(user);
        // 绑定角色
        RelationUserRole relationUserRole = new RelationUserRole(user.getId(), Role.USER_ID);
        boolean saveRoleRes = relationUserRoleService.save(relationUserRole);

        return saveUserRes && saveRoleRes;
    }

}
