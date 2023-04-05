package com.zjc.blog.security.service;

import com.zjc.blog.entity.User;
import com.zjc.blog.security.details.AuthCodeUserDetails;
import com.zjc.blog.security.details.NormalUserDetails;
import com.zjc.blog.service.AuthCodeService;
import com.zjc.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 验证码登录验证逻辑，使用账号验证码
 */
@Service
public class AuthCodeUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthCodeService authCodeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户是否存在
        User user = userService.getByUsername(username);
        if (user == null) {
            return null;
        }
        return new AuthCodeUserDetails(user, authCodeService.getAuthCode(username), new ArrayList<>());
    }

}
