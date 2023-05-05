package com.lexuefa.filter;

import com.lexuefa.comment.exception.ApiException;
import com.lexuefa.comment.result.IErrorCode;
import com.lexuefa.comment.result.ResultObject;
import com.lexuefa.entity.user.LoginUser;
import com.lexuefa.entity.user.User;
import com.lexuefa.service.UserService;
import com.lexuefa.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 认证过滤器
 *
 * @author ukir
 * @date 2023/05/02 22:15
 **/
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private UserService userService;

    @Value("${Redis.cacheExpire}")
    private int cacheExpire;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //获取请求头的token
        String token = request.getHeader("token");

        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
        } else {
            //解析token
            Integer userid = jwtTokenUtil.getUserIdFromToken(token);
            if (userid == null) {
                throw new ApiException("token非法");
            }

            Boolean exepired = jwtTokenUtil.isExepired(token);
            if(!exepired){
                //从redis中获取token
                String result = redisTemplate.opsForValue().get("login:" + userid);

                //从redis中获取用户信息
//            LoginUser loginUser = redisCache.getCacheObject("login:" + userid);
                if (!StringUtils.hasText(result)) {
                    throw new ApiException("用户未登录");
                }
                User user = userService.getById(userid);
                LoginUser loginUser = new LoginUser(user);

                //封装Authentication对象存入SecurityContextHolder
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(loginUser, null, null);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                //放行
                filterChain.doFilter(request, response);
            }else {
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request,response);
            }


        }


    }
}
