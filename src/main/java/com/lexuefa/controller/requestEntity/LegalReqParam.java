package com.lexuefa.controller.requestEntity;

import lombok.Data;

/**
 * @author ukir
 * @date 2023/04/19 21:08
 **/
@Data
public class LegalReqParam {
    /**
     * 法律标题
     */
    public String legalName;

    /**
     * 条文内容
     */
    public String article;

    /**
     * 一级分类
     */
    public String topCategory;
    /**
     * 二级分类
     */
    public String secondCategory;

    /**
     * 法律编号
     */
    public String legalNo;
    
    public int pageNo;
    
    public int pageSize;
    
}
