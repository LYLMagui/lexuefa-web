package com.lexuefa.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lexuefa.controller.reqEntity.LoginReq;
import com.lexuefa.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    Integer searchIdByOpenId(String openId);

    /**
     * 保存用户数据
     * @param loginReq
     */
    Integer saveUser(@Param("loginReq") LoginReq loginReq, @Param("openId") String openId, @Param("salt") String salt);

    /**
     * 通过账号查询用户
     * @param userName
     * @return
     */
    Integer searchIdByUserName(String userName);
}
