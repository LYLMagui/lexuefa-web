package com.lexuefa.config;

import com.lexuefa.filter.JwtAuthenticationTokenFilter;
import com.lexuefa.service.MyUserDetailsService;
import com.lexuefa.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * SpringSecurity配置类
 * @author ukir
 * @date 2023/05/02 21:46
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Resource
    private MyUserDetailsService myUserDetailsService;
    
    /**
     * SpringSecurity使用PasswordEncoder来进行密码校验
     * @return
     */
    //注册可使用BCryptPasswordEncoder的encode()进行加密,使用matches()进行密码比对
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //对于登录接口允许匿名访问
                .antMatchers("/user/login/").anonymous()
                .antMatchers("/legal/querySecType/**","/legal/queryLawType","/user/reflashToken").permitAll()
                //除上面的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        //开启过滤
        http.addFilterBefore(jwtAuthenticationTokenFilter,
                UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        String s;
        //使用自定义的UserDetailService
        auth.userDetailsService(myUserDetailsService);
    }

    /**
     * 通过AuthenticationManager的authenticate方法来进行用户认证
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
