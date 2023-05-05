package com.lexuefa.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 密码加密工具类
 * @author ukir
 * @date 2023/05/02 00:30
 **/
@Slf4j
public class PassowrdUtil {
    /**
     * 生成盐
     * @return
     */
    public static String generateSalt(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 加盐并生成最终密码
     * @param password 需要验证的明文密码
     * @param salt 盐
     * @return 加盐后的最终密码
     */
    public static String encrypt(String password,String salt){
        //生成加盐后的密码
        String saltPassword = DigestUtil.sha256Hex((salt + password).getBytes());
        //生成最终的密码
        return salt + "$" + saltPassword;
    }
    
    public static boolean checkPassword(String inputPassword,String finalPassword){
        //
        if(StringUtils.hasLength(inputPassword) && StringUtils.hasLength(finalPassword) && finalPassword.length() == 97){
            //a.首先从最终的密码中得到盐值
            //使用$将finalPassword划分成两个部分，前面的32位的部分就是盐值
            //注意：这里的$是被认为是一个通配符，所以要转义一下
            String salt = finalPassword.split("\\$")[0];
            
            //b.使用之前加密的方法，生成最终的密码格式（待验证）
            String checkPassword = encrypt(inputPassword,salt);
            log.info("用户密码长度：{}",checkPassword.length());
            log.info("最终密码长度：{}",finalPassword.length());
            if(finalPassword.equals(checkPassword)){
                return true;
            }
        }
        return false;
    }
}
