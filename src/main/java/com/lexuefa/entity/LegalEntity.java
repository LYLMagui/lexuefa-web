package com.lexuefa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 法律实体类
 *
 * @author ukir
 * @date 2023/04/19 16:45
 **/
@Data
@Accessors(chain = true)
@TableName("legal_document")
public class LegalEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField(value = "legal_no")
    private String legalNo;

    @TableField(value = "legal_name")
    private String legalName;

    @TableField(value = "top_category")
    private String topCategory;

    @TableField(value = "second_category")
    private String secondCategory;

    @TableField(value = "version")
    private Integer version;

    @TableField(value = "activity")
    private Integer activity;

    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(value = "publish_time")
    private LocalDate publishTime;

    @TableField(value = "start_time")
    private LocalDate startTime;

    @TableField(value = "document_number")
    private String documentNumber;

    @TableField(value = "enact_agency")
    private String enactAgency;

    @TableField(value = "preface")
    private String preface;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;

}
