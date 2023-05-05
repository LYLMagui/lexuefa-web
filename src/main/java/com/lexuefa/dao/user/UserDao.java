package com.lexuefa.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lexuefa.controller.reqEntity.LoginReq;
import com.lexuefa.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * UserDao接口
 * @author ukir
 * @date 2023/04/28 22:53
 **/
@Mapper
public interface UserDao extends BaseMapper<User> {

    /**
     * 通过openId查询用户
     * @return
     */
    User searchIdByOpenId(String openId);

    /**
     * 保存用户数据
     * @param loginReq
     */
    void saveUser(@Param("loginReq") LoginReq loginReq, @Param("openId") String openId);

    /**
     * 通过微信注册
     * @param map
     */
    void saveUserByWx(Map<String,String> map);
    
    /**
     * 通过账号查询用户
     * @param userName
     * @return
     */
    User searchIdByUserName(String userName);
}
