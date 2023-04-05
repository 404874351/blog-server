package com.zjc.blog.security.handler;

import com.zjc.blog.entity.Role;
import com.zjc.blog.entity.User;
import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.security.token.NormalAuthenticationToken;
import com.zjc.blog.service.JwtService;
import com.zjc.blog.service.RoleService;
import com.zjc.blog.vo.ResponseResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * spring security 登录成功的后续处理
 */
@Data
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RoleService roleService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 获取认证与授权所需的username和权限列表
        String username = authentication.getName();
        User user = (User) authentication.getDetails();
        List<GrantedAuthority> authorityList = getAuthorityListByUserId(user.getId());
        // 创建token，添加自定义载荷，暂定id
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        String token = jwtService.createToken(username, map);
        // redis存储token和权限列表
        boolean redisResult = jwtService.setTokenAndAuthority(username, token, authorityList, jwtService.getMaxAge());

        // 根据redis存储结果返回数据
        ResponseResult res;
        if(redisResult) {
            // 返回token到客户端，作为登录凭证
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            res = ResponseResult.success(data);
        } else {
            // redis清空出错，则清空缓存，返回错误信息
            jwtService.delTokenAndAuthority(username);
            res = ResponseResult.fail(StateCodeMsg.LOGIN_FAIL, null);
        }

        ResponseResult.outputSuccessResult(response, res);
    }

    public List<GrantedAuthority> getAuthorityListByUserId(Integer userId) {
        List<Role> roleList = roleService.listByUserId(userId);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (Role role : roleList) {
            // 对于禁用的角色，则不算有效授权
            if (role.isEntityDeactivated()) {
                continue;
            }
            authorityList.add(new SimpleGrantedAuthority(role.getCode()));
        }
        return authorityList;
    }
}
