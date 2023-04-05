package com.zjc.blog.service.impl;

import com.zjc.blog.service.JwtService;
import com.zjc.blog.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer";

    @Value("${auth.jwt.max-age}")
    private long maxAge;

    @Value("${auth.jwt.secret}")
    private String secret;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public long getMaxAge() {
        return maxAge;
    }

    /**
     * 创建token，携带username和自定义数据
     * @param username 用户名
     * @param map 自定义数据
     * @return
     */
    @Override
    public String createToken(String username, Map<String, Object> map) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + 1000 * maxAge);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return jwt;
    }

    /**
     * 解析token
     * @param jwt token
     * @return
     */
    @Override
    public Claims parseToken(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
            return claims;
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    /**
     * token过期判断
     * @param claims
     * @return
     */
    @Override
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    /**
     * 通过请求头的Authorization获取token
     * @param authorization
     * @return
     */
    @Override
    public String getJwtByAuthorization(String authorization) {
        if(authorization == null) {
            return null;
        }
        // 去除固定前缀和空白符
        String jwt = authorization.replaceAll(TOKEN_PREFIX, "");
        jwt = jwt.replaceAll("\\s*", "");
        return jwt;
    }

    /**
     * 缓存用户token和权限列表，有效值 15min
     * @param username 用户名
     * @param token token
     * @param authorities 权限列表
     * @param time 过期时间，单位秒
     * @return
     */
    @Override
    public boolean setTokenAndAuthority(String username, String token, List<GrantedAuthority> authorities, long time) {
        // 每次仅保留最新内容，清空之前的内容
        this.delTokenAndAuthority(username);
        if(!redisUtils.set(username + ":token", token, time)) {
            return false;
        }
        List<Object> authorityStringList = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            authorityStringList.add(authority.toString());
        }

        return redisUtils.lSet(username + ":authority", authorityStringList, time);
    }

    /**
     * 获取token缓存
     * @param username 用户名
     * @return token
     */
    @Override
    public String getToken(String username) {
        return (String) redisUtils.get(username + ":token");

    }

    /**
     * 获取权限列表
     * @param username 用户名
     * @return 权限列表
     */
    @Override
    public List<GrantedAuthority> getAuthority(String username) {
        List<Object> list = redisUtils.lGet(username + ":authority", 0, -1);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Object obj : list) {
            authorities.add(new SimpleGrantedAuthority(obj.toString()));
        }
        return authorities;
    }

    /**
     * 删除用户token和权限列表
     * @param username 用户名
     */
    @Override
    public void delTokenAndAuthority(String username) {
        redisUtils.del(username + ":token");
        redisUtils.del(username + ":authority");
    }

}
