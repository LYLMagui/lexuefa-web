package com.lexuefa.entity.legal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 法律章节实体类
 * @author ukir
 * @date 2023/04/23 16:21
 **/
@Data
@TableName("legal_chapters")
public class Chapter {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    @TableField("legal_no")
    private String legalNo;
    
    @TableField("chapter")
    private String chapter;
    
    @TableField("chapter_title")
    private String chapterTitle;
    
    private List<Section> sections;
    
    @TableField(exist = false)
    private List<Article> articles;
}
