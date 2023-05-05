package com.lexuefa.utils;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Jwt工具类
 *
 * @author ukir
 * @date 2023/03/01 21:12
 **/
@Component
@Slf4j
@Data
public class JwtTokenUtil implements Serializable {
    /**
     * 密钥
     */
    @Value("${jwt.jwtSecret}")
    private String jwtSecret = "@Aa123456";

    /**
     * 过期时间
     */
    @Value("${jwt.expiration}")
    private int expiration;

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
        Date date = DateUtil.offset(new Date(), DateField.HOUR, expiration);
        return Jwts.builder().setClaims(claims).setExpiration(date).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
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
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
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
     * 从token中获取用户id
     *
     * @param token
     * @return
     */
    public Integer getUserIdFromToken(String token) {
        Integer userId;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = (Integer) claims.get("userId");
        } catch (Exception e) {
            userId = null;
        }
        return userId;
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

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2ODMyMTIxNjEsInVzZXJJZCI6NTksImNyZWF0ZWQiOjE2ODMyMTIwNDEyNjh9.Dmq46rdKMtJM_pf_lWV11PfnFeD4KZhixgXVEuYE2RRENsQvqsT6bS8WgWQk7YSZbL4DeuXjLjpRgvcJIIhHpg";
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        Boolean exepired = jwtTokenUtil.isExepired(token);
        //刷新token
        String refreshToken = null;
        if(exepired){
             refreshToken = jwtTokenUtil.refreshToken(token);
        }
        log.info("是否过期：{}",exepired);
        log.info("新token：{}",refreshToken);
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
            claims.put("created", new Date());
            refreshToken = generateToken(claims);
        } catch (Exception e) {
            refreshToken = null;
        }
        return refreshToken;
    }
    
    public void verifierToken(String token){
        //创建算法对象
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        //使用算法对象解密
        JWTVerifier verifier = JWT.require(algorithm).build();
        //调用验证方法
        verifier.verify(token);
    }
    
}
