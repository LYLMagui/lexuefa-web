package com.lexuefa.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 *
 * @author ukir
 * @date 2023/03/01 21:12
 **/
@Component
@Data
public class JwtTokenUtil implements Serializable {
    /**
     * 密钥
     */
    @Value("${jwt.jwtsecret}")
    private String jwtSecret;

    /**
     * 过期时间
     */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 请求头
     */
    private String heard;

    /**
     * 生成token
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        //过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    /**
     * 获取token中的声明部分
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 提供供外界访问的创建token的方法
     *
     * @param id
     * @return
     */
    public String generateToken(Integer id) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("userId", id);
        claims.put("created", new Date());
        return generateToken(claims);
    }

    /**
     * 从token中获取用户名称
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @return
     */
    public Boolean isExepired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("create", new Date());
            refreshToken = generateToken(claims);
        } catch (Exception e) {
            refreshToken = null;
        }
        return refreshToken;
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @param userDetails
     * @return
     */
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        JwtUser user = (JwtUser) userDetails;
//        String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isExepired(token));
//    }
}
