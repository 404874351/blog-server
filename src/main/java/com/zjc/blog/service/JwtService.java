package com.zjc.blog.service;


import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface JwtService {

    long getMaxAge();

    String createToken(String username, Map<String, Object> map);

    Claims parseToken(String jwt);

    boolean isTokenExpired(Claims claims);

    String getJwtByAuthorization(String authorization);

    boolean setTokenAndAuthority(String username, String token, List<GrantedAuthority> authorities, long time);

    String getToken(String username);

    List<GrantedAuthority> getAuthority(String username);

    void delTokenAndAuthority(String username);

}
