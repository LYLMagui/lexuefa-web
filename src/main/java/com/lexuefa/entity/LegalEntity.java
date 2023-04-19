package com.lexuefa.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.DataTruncation;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 法律实体类
 *
 * @author ukir
 * @date 2023/04/19 16:45
 **/
@Data
@TableName(value = "legal_document")
public class LegalEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "legal_no")
    private String legalNo;
    @TableField(value = "legal_name")
    private String legalName;
    @TableField(value = "chapter")
    private String chapter;
    @TableField(value = "article_no")
    private String articleNo;
    @TableField(value = "article")
    private String article;
    @TableField(value = "chapter_title")
    private String chapterTitle;
    @TableField(value = "top_category")
    private String topCategory;
    @TableField(value = "second_category")
    private String secondCategory;
    @TableField(value = "year")
    private String year;
    @Version
    private int version;
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
    @TableLogic
    @TableField(value = "is_deleted")
    private Integer IsDeleted;

}
