package com.zjc.blog.controller;

import com.zjc.blog.dto.UserInfoDto;
import com.zjc.blog.service.QiniuService;
import com.zjc.blog.service.UserService;
import com.zjc.blog.vo.UserPasswordVo;
import com.zjc.blog.vo.UserRegisterVo;
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
@RequestMapping("/qiniu")
public class QiniuController {

    @Autowired
    private QiniuService qiniuService;

    /**
     * 获取token，如需覆盖上传需传递key
     * @return
     */
    @GetMapping("/token")
    public String token(@RequestParam(value = "key", required = false) String key) {
        return qiniuService.createToken(key);
    }
}
