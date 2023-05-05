package com.lexuefa.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 开启CORS跨域共享
 * @author ukir
 * @date 2023/05/04 15:19
 **/
@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration configuration = new CorsConfiguration();
        //设置允许的网站域名
        configuration.addAllowedOrigin("https://servicewechat.com/wx30f5687a058db2e0/devtools/page-frame.html");
        //放行放行全部原始头信息
        configuration.addAllowedHeader("*");
        //允许所有请求方法跨域调用
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return new CorsFilter(source);
    }
}
