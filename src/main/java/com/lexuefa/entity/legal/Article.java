package com.lexuefa.entity.legal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 法律条文实体类
 * @author ukir
 * @date 2023/04/23 16:17
 **/
@Data
@TableName("legal_chapters")
public class Article {
    
    @TableField("atricle_no")
    private String articleNo;
    
    @TableField("article")
    private String article;
}
