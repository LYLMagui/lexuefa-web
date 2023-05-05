package com.lexuefa.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ukir
 * @date 2023/04/28 22:49
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    /**
     * 主键ID
     */
    @TableId
    private Integer id;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 头像
     */
    @TableField("user_img")
    private String userImg;

    /**
     * 个性签名
     */
    
    private String signature;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 是否删除（0 未删除，1删除）
     */
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 是否锁定（0 未锁定，1锁定）
     */
    private Integer locked;
    
    /**
     * 微信昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 微信头像
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 城市
     */
    private String city;
    
    @TableField("open_id")
    private String openId;

    @TableField("session_key")
    private String sessionKey;
    
}