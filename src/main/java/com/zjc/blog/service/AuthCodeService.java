package com.zjc.blog.service;


import io.jsonwebtoken.Claims;

public interface AuthCodeService {

    String createAuthCode();

    String getAuthCode(String username);

    boolean setAuthCode(String username, String code);

    void delAuthCode(String username);

    boolean canSend(String username);

    boolean sendAuthCode(String phone, String code) throws Exception;

}
