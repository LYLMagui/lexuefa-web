package com.lexuefa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ukir
 * @date 2023/04/22 13:35
 **/
@Data
@TableName("legal_type")
public class LegalType {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "top_category")
    private String topCategory;

    @TableField(value = "top_name")
    private String topName;
    @TableField(value = "second_category")
    private String secondCategory;
    @TableField(value = "second_name")
    private String secondName;

}
