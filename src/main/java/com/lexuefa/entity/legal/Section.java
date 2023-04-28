package com.lexuefa.entity.legal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 法律内容的节实体类
 * @author ukir
 * @date 2023/04/24 22:05
 **/
@Data
@TableName("legal_chapter")
public class Section {
    private String sectionNo;

    private String sectionTitle;

    @TableField(exist = false)
    private List<Article> articles;
}
