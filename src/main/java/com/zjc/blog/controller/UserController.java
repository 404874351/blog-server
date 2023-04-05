package com.zjc.blog.controller;

import com.zjc.blog.dto.UserInfoDto;
import com.zjc.blog.enums.StateCodeMsg;
import com.zjc.blog.exception.ServiceException;
import com.zjc.blog.service.AuthCodeService;
import com.zjc.blog.service.UserService;
import com.zjc.blog.vo.UserAuthCodeVo;
import com.zjc.blog.vo.UserPasswordVo;
import com.zjc.blog.vo.UserRegisterVo;
import com.zjc.blog.vo.UserUpdateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.zjc.blog.security.filter.JwtAuthenticationFilter.REQUEST_USER_ID_NAME;

@Slf4j
@Transactional(rollbackFor={Exception.class})
@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthCodeService authCodeService;


    /**
     * 用户请求验证码
     * @param userAuthCodeVo
     * @return
     */
    @PostMapping("/code")
    public boolean authCode(@Validated UserAuthCodeVo userAuthCodeVo) throws Exception {
        if (!authCodeService.canSend(userAuthCodeVo.getPhone())) {
            throw new ServiceException(StateCodeMsg.AUTH_CODE_SEND_TOO_FAST);
        }
        String code = authCodeService.createAuthCode();
        boolean setRes = authCodeService.setAuthCode(userAuthCodeVo.getPhone(), code);
        boolean sendRes = authCodeService.sendAuthCode(userAuthCodeVo.getPhone(), code);
        return setRes && sendRes;
    }

    /**
     * 用户注册，该接口为白名单接口
     * @param userRegisterVo
     * @return
     */
    @PostMapping("/register")
    public boolean register(@Validated UserRegisterVo userRegisterVo) {
        String code = userRegisterVo.getCode();
        String phone = userRegisterVo.getPhone();
        if (!code.equals(authCodeService.getAuthCode(phone))) {
            throw new ServiceException(StateCodeMsg.AUTH_CODE_INVALID);
        }

        authCodeService.delAuthCode(phone);
        return userService.register(userRegisterVo);
    }

    /**
     * 获取用户个人信息
     * @param request
     * @return
     */
    @GetMapping("/info")
    public UserInfoDto userInfo(HttpServletRequest request) {
        return userService.getUserInfoDtoById((Integer) request.getAttribute(REQUEST_USER_ID_NAME));
    }

    /**
     * 修改密码
     * @param userPasswordVo
     * @return
     */
    @PostMapping("/password")
    public boolean updatePassword(@Validated UserPasswordVo userPasswordVo, HttpServletRequest request) {
        // 从登录状态中获取用户id
        userPasswordVo.setId((Integer) request.getAttribute(REQUEST_USER_ID_NAME));
        return userService.updatePassword(userPasswordVo);
    }

    /**
     * 更新用户信息
     * @param userUpdateVo
     * @return
     */
    @PostMapping("/update")
    public boolean update(@Validated UserUpdateVo userUpdateVo, HttpServletRequest request) {
        // 从登录状态中获取用户id
        userUpdateVo.setId((Integer) request.getAttribute(REQUEST_USER_ID_NAME));
        return userService.updateById(userUpdateVo);
    }
}
