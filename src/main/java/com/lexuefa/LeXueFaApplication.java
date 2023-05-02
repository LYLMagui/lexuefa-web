package com.lexuefa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ukir
 * @date 2023/04/18 19:20
 **/
@Slf4j
@SpringBootApplication
public class LeXueFaApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeXueFaApplication.class,args);
        log.info("=============== 程序启动完毕 ===============");
    }
    
}
